# Yes_SEP2: A client server game rental system for physical copies
As ownership of physical copies of video games slowly transitions to a subscription model, a key joy of community is lost: sharing. To combat this, a game rental platform has been developed, which was implemented as a client server system in Java, using JavaFX for the GUI, communication using remote method invocation (RMI), and persisted with a PostgreSQL database.

![image](https://github.com/chrfoyer/Yes_SEP2/assets/92271155/c679c170-8ba4-45c4-af56-9b3d7db8cdf9)
![image](https://github.com/chrfoyer/Yes_SEP2/assets/92271155/e7efb9a2-2e8b-4b7e-957a-876d1458e171)


# Installation and usage
1. Install Java 11 and check the version with the following command:
```
java -version
```
2. Install PostgreSQL.
3. Run `Database\GamesRentalDB\sql_test_ddl.sql`
2. Locate the executables in `Appendices\D - Source Code\C - Production ready JAR files`. Then run the server by double clicking runServer.bat.
3. Use the credentials you defined for Postgres to connect to the database.
4. Return to `Appendices\D - Source Code\C - Production ready JAR files` and double click runClient.bat.
5. For administrator access, use the following very secure credentials:
  - Username: admin
  - Password: admin
6. Refer to the user guide for detailed instruction

# Context within education
Whereas the first semester of the software engineering program at VIA University College focuses on single user systems, the second has a focus on client server systems. Software engineers may learn about client-server systems after mastering single-user systems to understand the complexities of distributed computing, network communication, and managing data across multiple users. This knowledge is crucial for developing applications that can scale and provide efficient services in a networked environment, addressing challenges beyond the scope of individual user interactions.

# Design
As a university project, readmes like this should aim to answer the following questions: what knowledge has been demonstrated, and what was learned? For detailed answers, please look at the project and process report in the root directory, which detail the analysis, design, implementation, testing, as well as how SCRUM was used during development. Here we will summarize some of the design decisions to give better context to the structure.

## Use cases
Although a large variety of design diagrams are present within the project report, the following diagram succinctly describes the desired workflow of the GUI as well as a brief summary what can be done.
![image](https://github.com/chrfoyer/Yes_SEP2/assets/92271155/e1f57431-244d-4752-9216-8339d4faae09)

Having users for such a system enables the implementation of secure authentication mechanisms, allowing users to log in with their credentials, while the creation of admin accounts provides privileged access for system management and oversight. Once logged in, users can view the games available, rent games, and see their current rentals. On the other hand, administrator accounts were tasked with managing the userbase as well as inventory.

## Structure
In terms of the structure for the code, a basic MVVM (model view view model) approach was taken for improved reusability. The model held the business logic of how the entities should behave, the controllers interacted with the JavaFX GUI elements, and the view models contained the operational behavior. The communication was implemented using RMI, which involved using remote model that pass messages containing serialized model objects over TCP. To persist this to the PostgreSQL database, data access objects (DAOs) were used to execute a collection of SQL queries.

![image](https://github.com/chrfoyer/Yes_SEP2/assets/92271155/dc889822-1631-4bbe-b4f5-0865b38275c1)

## Data model
Given the entities seen in the model, a basic mental image can be created to describe how they interact to achieve the goal of renting games.

![image](https://github.com/chrfoyer/Yes_SEP2/assets/92271155/3869cf8e-8b97-4498-8013-73755943494c)

Although the goal was to create a database that was normalized in the third form, this was not achieved. There still exist a number of transitive dependencies that cause data to be duplicated. Take a look at the `Games` and consider how the same game across multiple consoles would create multiple lines sharing much of the same information.

# Code Snippets
Since renting games is central to the purpose of the project, these code snippets will follow how the rental itself is implemented. When a user attempts to rent a game, the following method is called in the `BrowseViewController`
```java
public void rent()
    {
        if (CurrentlyLoggedUser.getLoggedInUser().hasSubscription())
        {
            SimpleGameViewModel temp = table.getSelectionModel().getSelectedItem();
            Game selectedGame = temp.getGame();

            if (browseViewModel.ageCheck(selectedGame))
            {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to rent game? -> " + selectedGame.getName());
                Optional<ButtonType> option = alert.showAndWait();
                if (option.isPresent() && option.get() == ButtonType.OK)
                {
                    if (browseViewModel.rentGame(selectedGame))
                    {


                        //displaying confirmation message
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Congratulations, Game rented");
                        alert.setContentText("Now you have the game (" + selectedGame.getName()
                                + ") for 14 days\n"
                                + "If you would like to extend it you may do that on your profile!");
                        alert.showAndWait();
                        browseViewModel.reset();
                    }
                }
            }
            //   browseViewModel.reset();
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "You cannot rent games without an active subscription!");
            alert.showAndWait();
        }

    }
```
This in turn calls the following method from `BrowseViewModel`
```java
public boolean rentGame(Game game)
    {
        try
        {
            if (game == null)
                throw new IllegalArgumentException("You must make a selection first!");
            model.rentGame(game, CurrentlyLoggedUser.getLoggedInUser());
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            errorLabel.set(e.getMessage());
        }
        return false;
    }
```
The `model` in this case is the `RemoteModel`, which is used for communication in this implementation of RMI and is identical of the server and client side. This is an interface that is implemented in the `ModelManger` class on the server side.
```java
public void rentGame(Game game, User user) throws SQLException
    {
        if (game == null)
        {
            throw new IllegalArgumentException("Game to rent cant be null");
        } else if (game.getEsrb().equals("M") && user.getAge() < 17)
        {
            throw new IllegalAccessError("You are too young to rent this game!");
        } else if (game.getEsrb().equals("AO") && user.getAge() < 18)
        {
            throw new IllegalAccessError("You are too young to rent this game!");
        } else
        {
            games.findGameInList(game).rentGame();
            gameDAO.rent(game, user);
            transactionDAO.create(new Transaction(user.getUsername(), "Rented " + game.getName()));
            refreshTransactionList();
            refreshGameList();
            System.out.println(user.getUsername() + " rented " + game.getName() + " on " + game.getConsole());
        }
    }
```
The DAO interface is implemented in the GameImpl which then sends the rental information to the database with the following query.
```java
public void rent(Game game, User user) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO rentals " +
                            "(game_id, username, date_rented, rental_length_allowed, active, overdue) " +
                            "VALUES (?, ?, CURRENT_DATE, 14, TRUE, FALSE); " +

                            "UPDATE games " +
                            "SET rented = TRUE " +
                            "WHERE id = ?; "
            );
            st.setInt(1, game.getId());
            st.setString(2, user.getUsername());
            st.setInt(3, game.getId());
            st.executeUpdate();
            st.close();
        }
    }
```
# Tests
In broad terms, the testing is limited, but very pointed. There are a few unit tests on the client side that serve to demonstrate an understanding of ZOMBIES more than verify the functionality. To do that, a number of test cases were written and performed to verify the core requirements were met. These can be found within the Project Report.

![image](https://github.com/chrfoyer/Yes_SEP2/assets/92271155/4405b6cf-c0cd-4ebd-8000-72fe7346c10d)

# Conclusion
By creating a client server game rental system, the team successfully demonstrated they were able to communicate with objects and persist them in a database. The real world application is limited, but for a school project, the scope is wide and the use cases set out were implemented. Additionally, the design and process documentation is extensive and exhaustive.
