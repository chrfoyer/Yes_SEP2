package Model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The game is the object that is rented in the system.
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class Game implements Serializable
{
    private final int id;
    private final LocalDate dateAdded;
    private String name;
    private String producer;
    private String console;
    private boolean rented;
    private int daysLeft;
    private int reviewCount;
    private int reviewSum;
    private float reviewAverage;
    private String esrb;

    /**
     * An 11 argument constructor used to copy the information from the database.
     *
     * @param id            id of the game
     * @param name          name of the game
     * @param producer      producer of the game
     * @param console       the console the game is played on
     * @param rented        if the game is rented or not
     * @param daysLeft      days left until the game has to be returned
     * @param reviewCount   the number of reviews for the game
     * @param reviewSum     the total sum of all the reviews for the game
     * @param reviewAverage the average of all the reviews for the game
     * @param esrb          the rating of the game
     * @param dateAdded     date the game is added
     */
    public Game(int id, String name, String producer, String console, boolean rented, int daysLeft, int reviewCount, int reviewSum, float reviewAverage, String esrb, LocalDate dateAdded)
    {
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.console = console;
        this.rented = rented;
        this.daysLeft = daysLeft;
        this.reviewCount = reviewCount;
        this.reviewSum = reviewSum;
        this.reviewAverage = reviewAverage;
        this.esrb = esrb;
        this.dateAdded = dateAdded;
    }

    /**
     * A 4 argument constructor used to create a game from the information given in the add game view.
     *
     * @param name     name of game
     * @param producer producer of the game
     * @param console  platform on which the game can be played
     * @param esrb     rating of the game
     */
    public Game(String name, String producer, String console, String esrb)
    {
        if (!(esrb.equals("E") || esrb.equals("E10+") || esrb.equals("T")
                || esrb.equals("M") || esrb.equals("AO")))
        {
            throw new IllegalArgumentException("Unknown rating");
        }
        if (!(console.equals("PC") || console.equals("PlayStation")
                || console.equals("Xbox") || console.equals("Nintendo")))
        {
            throw new IllegalArgumentException("Unknown console");
        }
        id = 0;
        this.esrb = esrb;
        this.producer = producer;
        this.name = name;
        rented = false;
        daysLeft = 0;
        this.console = console;
        this.dateAdded = LocalDate.now();
        reviewCount = 0;
        reviewSum = 0;
        reviewAverage = 0;
    }

    /**
     * Checks if obj is equals to a game
     *
     * @param obj fed into method to compare to a game
     * @return boolean if the games are equal or not
     */
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Game))
        {
            return false;
        }
        Game game = (Game) obj;
        return name.equals(game.getName()) && rented == game.isRented() && producer.equals(game.getProducer()) && console.equals(game.getConsole());
    }

    /**
     * Prints string of name of game
     *
     * @return name of game
     */
    public String getName()
    {
        return name;
    }

    /**
     * Changes the name of the game to a new given name
     *
     * @param name new name of the game
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Checks if game is rented
     *
     * @return boolean of if game is rented or not
     */
    public boolean isRented()
    {
        return rented;
    }

    /**
     * Getter for the days left in the rental period for the game
     *
     * @return int of days left
     */
    public int getDaysLeft()
    {
        return daysLeft;
    }

    /**
     * Setter for the days left in the rental period for the game
     *
     * @param daysLeft int of days left in the rental period
     */
    public void setDaysLeft(int daysLeft)
    {
        this.daysLeft = daysLeft;
    }

    /**
     * Decreases the days left in the rental period. If the game is not rented, an exception is thrown. If the game ran
     * out of days also sets rented to false
     *
     * @author Raedrim
     */
    public void decrementDaysLeft()
    {
        if (rented)
        {
            daysLeft--;
            if (daysLeft <= 0)
            {
                System.out.println(name + " Ran out of time, game not rented anymore");
            }
        } else
        {
            throw new IllegalStateException(
                    "Game is not currently rented, so the days can't be decreased.");
        }
    }

    /**
     * Prints out a string of the game
     *
     * @return name of game and whether the game is rented or not and the days left before the game has to be returned
     */
    public String toString()
    {
        String str = "Name: " + name + " Rented: " + rented;
        if (rented)
        {
            str += "\nDays Left: " + daysLeft;
        }
        return str;
    }

    /**
     * rents a game by setting rented to true and daysLeft to 14
     */
    public void rentGame()
    {
        if (rented)
        {
            throw new IllegalStateException("Game is already rented!");
        } else
        {
            this.rented = true;
            this.daysLeft = 14;
        }
    }

    /**
     * returns a game by setting rented to false and days left to 0.
     */
    public void returnGame()
    {
        if (!rented)
        {
            throw new IllegalStateException(
                    "Game is not rented so it cannot be returned!");
        } else
        {
            this.rented = false;
            this.daysLeft = 0;
        }
    }

    /**
     * Gets the production company of the game
     *
     * @return production company
     */
    public String getProducer()
    {
        return producer;
    }

    /**
     * Changes the production house of the game to a new given production house
     *
     * @param producer new production house of the game
     */
    public void setProducer(String producer)
    {
        this.producer = producer;
    }

    /**
     * Gets the review of the game (1-5)
     *
     * @return a decimal number review of the game
     */
    public float getReview()
    {
        return reviewAverage;
    }

    /**
     * Gets the international video game rating for the game
     *
     * @return String of the ESRB rating
     */
    public String getEsrb()
    {
        return esrb;
    }

    /**
     * Changes the ESRB rating of the game to a new rating
     *
     * @param esrb new rating for the game
     */
    public void setEsrb(String esrb)
    {
        this.esrb = esrb;
    }

    /**
     * Gets the console that the game is played on
     *
     * @return console-platform the game is for
     */
    public String getConsole()
    {
        return console;
    }

    /**
     * sets the console of the game to console
     *
     * @param console what to set the games console to
     */
    public void setConsole(String console)
    {
        this.console = console;
    }

    /**
     * Returns the date which the Game object was created
     *
     * @return LocalDateTime
     */
    public LocalDate getDateAdded()
    {
        return dateAdded;
    }

    /**
     * leaves a review int 1-5 for a game by adding it to the reviewSum and incrementing reviewCount so that
     * reviewAverage can be updated
     *
     * @param review the int (1-5)
     */
    public void leaveReview(int review)
    {
        reviewCount++;
        reviewSum += review;
        reviewAverage = (float) reviewSum / reviewCount;
    }

    /**
     * method for getting game's ID
     *
     * @return int ID
     */
    public int getId()
    {
        return id;
    }

    /**
     * method for getting number of reviews a game has
     *
     * @return int number of reviews
     */
    public int getReviewCount()
    {
        return reviewCount;
    }

    /**
     * method for getting the sum of all reviews for a game
     *
     * @return int of sum of all reviews for a game
     */
    public int getReviewSum()
    {
        return reviewSum;
    }

    /**
     * method for getting the average of the reviews of a game
     *
     * @return float average of all reviews for a game
     */
    public float getReviewAverage()
    {
        return reviewAverage;
    }
}
