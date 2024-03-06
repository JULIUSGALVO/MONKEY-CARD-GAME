import java.util.*;

public class MONKEY_CARD_GAME {
    public static void main(String[] args) {
        displayAsciiArt();
        displayWelcomeMessage();

        Scanner scanner = new Scanner(System.in);
        
        // create and display the deck
        String[] deck = createDeck();
        displayDeck(deck);

        enter_to_continue("\nPRESS ENTER TO DETERMINE MONKEY CARD...");
        
        // randomly choose and remove the monkey card from the deck
        Random random = new Random();
        int ramdomNumber = random.nextInt(deck.length);
        String monkeyCard = deck[ramdomNumber];
        deck[ramdomNumber] = null;
        // display monkey card and current deck
        System.out.println( "~".repeat(15) + " Monkey Card " + "~".repeat(15));
        System.out.println("\n"+ " - "+ monkeyCard);
        displayDeck(deck);
        
        enter_to_continue("\nPRESS ENTER TO SHUFFLE THE DECK...");
        // shuffle and display the deck
        shuffleArray(deck);
        System.out.println("\n"+"~".repeat(9) + " SHUFFLED DECK " + "~".repeat(9));
        plainlyDisplayDeck(deck);
        System.out.println("\n"+ "~".repeat(34));

        // declare computer and user deck
        String[] computerDeck = new String [26];
        String[] userDeck = new String [26];

        // determine who to give card first
        String[] choices = new String[] {"computer", "user"};
        String choice = choices[random.nextInt(choices.length)];

        // allocate deck for both players
        int allocatedCardForComputer = 0;
        int allocatedCardForUser = 0;
        for (String card: deck) {
            if (card != null) {
                if (choice.equals("computer")) {
                    computerDeck[allocatedCardForComputer] = card;
                    allocatedCardForComputer ++;
                    choice = "user";
                }
                else {
                    userDeck[allocatedCardForUser] = card;
                    allocatedCardForUser ++;
                    choice = "computer";
                }
            }
        }

        // display both players' decks and paired cards
        enter_to_continue("\nPRESS ENTER TO ALLOCATE AND SEE BOTH PLAYERS' DECKS AND PAIRED CARDS...");
        System.out.println("\n"+"~".repeat(9) + " COMPUTER'S DECK " + "~".repeat(9));
        plainlyDisplayDeck(computerDeck);

        System.out.println("\n"+"~".repeat(5) + " COMPUTER'S PAIRED CARDS " + "~".repeat(5));
        findAndDisplayMatchingCards(computerDeck);
        System.out.println("\n"+ "~".repeat(34));

        enter_to_continue("\nPRESS ENTER TO SEE YOUR DECK AND PAIRED CARDS...");
        System.out.println("\n"+"~".repeat(11) + " YOUR DECK " + "~".repeat(11));
        plainlyDisplayDeck(userDeck);

        System.out.println("\n"+"~".repeat(7) + " YOUR PAIRED CARDS " + "~".repeat(7));
        findAndDisplayMatchingCards(userDeck);
        System.out.println("\n"+ "~".repeat(34));
        
        // dispaly both players' remaining cards
        enter_to_continue("\nPRESS ENTER TO SEE BOTH PLAYERS' REMAINING CARDS...");
        System.out.println("\n"+"~".repeat(9) + " COMPUTER'S DECK " + "~".repeat(9));
        plainlyDisplayDeck(computerDeck);
        System.out.println("\n"+"~".repeat(11) + " YOUR DECK " + "~".repeat(11));
        plainlyDisplayDeck(userDeck);
        System.out.println("\n"+ "~".repeat(34));

        // determine first to draw a card
        String userChoice;
        while (true) {
            
            System.out.print("ENTER EITHER HEADS OR TAILS: ");
            userChoice = scanner.nextLine().toLowerCase().trim();
            if (userChoice.equals("heads") || userChoice.equals("tails")) {
                break;
            }
            else {
                System.out.println(" ~ INVALID INPUT");
            }
        }
        // randomly select between heads and tails
        String randomCoinSide = new String[] {"heads", "tails"}[random.nextInt(2)];
        // determine if user wins toss coin
        boolean userTurn;
        if (userChoice.equals(randomCoinSide)) {
            userTurn = true;
        }
        else {
            userTurn = false;
        }
        // game proper
        while (true) {
            if (userTurn) {
                System.out.print("\n"+"~".repeat(12) + " YOUR TURN " + "~".repeat(12));
                // get computer's card count and shuffle its deck
                int computerCardCount = getDeckLength(computerDeck);
                shuffleArray(computerDeck);
                // let user pick a from computer's deck
                System.out.println("\n"+"~".repeat(11) + " PICK A CARD " + "~".repeat(11));
                System.out.print(String.format("\nComputer currently has %s card/s.\nEnter a number from 1 to %s to pick a card from its deck: ", computerCardCount, computerCardCount));
                String chosenCard = scanner.nextLine().trim();
                // validate if user input is in range of computer's number of cards
                if (isValidInteger(chosenCard, 1, computerCardCount)) {
                    // get computer's remaining cards
                    String[] computerRemainingCards = getRemainingCards(computerDeck, computerCardCount);

                    // display picked card
                    String pickedCard = computerRemainingCards[Integer.parseInt(chosenCard)-1];
                    System.out.println("\n - You picked " + pickedCard);

                    // add picked card to user's deck and remove it from computer's deck
                    addElementToArray(userDeck, pickedCard);
                    removeElementFromArray(computerDeck, pickedCard);

                    // display computer's remainig cards
                    System.out.println("\n"+"~".repeat(4) + " COMPUTER REMAINING CARDS " + "~".repeat(4));
                    plainlyDisplayDeck(computerDeck);
                    System.out.println("\n"+ "~".repeat(34));

                    // display user's paired cards
                    System.out.println("\n"+"~".repeat(7) + " YOUR PAIRED CARDS " + "~".repeat(7));
                    findAndDisplayMatchingCards(userDeck);
                    System.out.println("\n"+ "~".repeat(34));
                    // display user's deck
                    System.out.println("\n"+"~".repeat(6) + " YOUR REMAINING CARDS " + "~".repeat(6));
                    plainlyDisplayDeck(userDeck);
                    System.out.println("\n"+ "~".repeat(34));

                    // check if user already has an empty deck
                    userTurn = false;
                    
                }
                else {
                    System.out.print("\n"+"~".repeat(10) + " INVALID INPUT " + "~".repeat(10));
                }
                
            }
            else {
                System.out.print("\n"+"~".repeat(9) + " COMPUTER'S TURN " + "~".repeat(9));
                enter_to_continue("\n\nPRESS ENTER TO LET COMPUTER PICK A CARD FROM YOUR DECK...");
                // shuffle and display user's deck
                shuffleArray(userDeck);
                System.out.println("\n"+"~".repeat(11) + " YOUR DECK " + "~".repeat(11));
                plainlyDisplayDeck(userDeck);
                System.out.println("\n"+ "~".repeat(34));
                
                // get user's card count
                int userCardCount = getDeckLength(userDeck);

                // get user's remaining cards
                String[] userRemainingCards = getRemainingCards(userDeck, userCardCount);
                
                // display picked card
                String pickedCard = userRemainingCards[random.nextInt(userCardCount)];
                System.out.println("\n - Computer picked " + pickedCard);

                // add picked card to computer's deck and remove it from user's deck
                addElementToArray(computerDeck, pickedCard);
                removeElementFromArray(userDeck, pickedCard);

                // display user's remainig cards
                System.out.println("\n"+"~".repeat(6) + " YOUR REMAINING CARDS " + "~".repeat(6));
                plainlyDisplayDeck(userDeck);
                System.out.println("\n"+ "~".repeat(34));

                // display computer's paired cards
                System.out.println("\n"+"~".repeat(3) + " COMPUTER'S PAIRED CARDS " + "~".repeat(3));
                findAndDisplayMatchingCards(computerDeck);
                System.out.println("\n"+ "~".repeat(34));

                // display computer's deck
                System.out.println("\n"+"~".repeat(4) + " COMPUTER REMAINING CARDS " + "~".repeat(4));
                plainlyDisplayDeck(computerDeck);
                System.out.println("\n"+ "~".repeat(34));
                userTurn = true;

            }

            if (getDeckLength(userDeck) == 0 || getDeckLength(computerDeck) == 0) {
                if (getDeckLength(userDeck) == 0){
                    System.out.println("\n"+"~".repeat(12) + " YOU WIN " + "~".repeat(12));
                    System.out.println("Computer's last card: " + getLastCard(computerDeck));
                }
                else {
                    System.out.println("\n"+"~".repeat(12) + " YOU LOSE " + "~".repeat(12));
                    System.out.println("Your last card: " + getLastCard(userDeck));
                }
                System.err.println("Monkey Card: " + monkeyCard);
                break;
            }
        }

    }

    public static String[] createDeck() {

        String[] deck = new String[52];
        int index = 0;

        for (String suit : new String[] {"Hearts", "Diamonds", "Clubs", "Spades"}) {
            for (String rank : new String[] {"Ace","2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"}) {
                deck[index] = rank + " of " + suit;
                index++;
            }
        }
        return deck;
    }
    public static void displayAsciiArt() {
        System.out.println(
            "                                                          .__  .__                                  __          \n" +
            "                                 __  _  __ ____ |  | |  |   ____  ____   _____   ____   _/  |_  ____  \n" +
            "                                 \\ \\/ \\/ // __ \\|  | |  | _/ ___\\/  _ \\ /     \\_/ __ \\  \\   __\\/  _ \\ \n" +
            "                                  \\     /\\  ___/|  |_|  |_\\  \\__(  <_> )  Y Y  \\  ___/   |  | (  <_> )\n" +
            "                                   \\/\\_/  \\___  >____/____/\\___  >____/|__|_|  /\\___  >  |__|  \\____/ \n" +
            "                                    \\/               \\/            \\/     \\/                 "
        );
    }

    public static void displayWelcomeMessage() {
        System.out.println(
            " \n" +
            "    ███╗   ███╗ ██████╗ ███╗   ██╗██╗  ██╗███████╗██╗   ██╗     ██████╗ █████╗ ██████╗ ██████╗      ██████╗  █████╗ ███╗   ███╗███████╗\n" +
            "    ████╗ ████║██╔═══██╗████╗  ██║██║ ██╔╝██╔════╝╚██╗ ██╔╝    ██╔════╝██╔══██╗██╔══██╗██╔══██╗    ██╔════╝ ██╔══██╗████╗ ████║██╔════╝\n" +
            "    ██╔████╔██║██║   ██║██╔██╗ ██║█████╔╝ █████╗   ╚████╔╝     ██║     ███████║██████╔╝██║  ██║    ██║  ███╗███████║██╔████╔██║█████╗  \n" +
            "    ██║╚██╔╝██║██║   ██║██║╚██╗██║██╔═██╗ ██╔══╝    ╚██╔╝      ██║     ██╔══██║██╔══██╗██║  ██║    ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝  \n" +
            "    ██║ ╚═╝ ██║╚██████╔╝██║ ╚████║██║  ██╗███████╗   ██║       ╚██████╗██║  ██║██║  ██║██████╔╝    ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗\n" +
            "    ╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝   ╚═╝        ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝      ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝\n" 
        );
    }

    public static void displayDeck(String[] deck) {
        System.out.println("\n" + "~".repeat(18) + " DECK " + "~".repeat(18));

        // Separate the deck into four lists based on suits
        List<String>[] suits = new List[4];
        for (int i = 0; i < 4; i++) {
            suits[i] = new ArrayList<>();
        }

        for (String card : deck) {
            if (card != null) {
                String suit = card.split(" ")[2];
                switch (suit) {
                    case "Hearts":
                        suits[0].add(card);
                        break;
                    case "Diamonds":
                        suits[1].add(card);
                        break;
                    case "Clubs":
                        suits[2].add(card);
                        break;
                    case "Spades":
                        suits[3].add(card);
                        break;
                }
            }
        }

        // Print each column
        int maxLength = Math.max(suits[0].size(), Math.max(suits[1].size(), Math.max(suits[2].size(), suits[3].size())));
        for (int i = 0; i < maxLength; i++) {
            for (int j = 0; j < 4; j++) {
                if (i < suits[j].size()) {
                    System.out.print(String.format("%-25s", suits[j].get(i)));
                } else {
                    System.out.print(String.format("%-25s", ""));
                }
            }
            System.out.println();
        }

        System.out.println("\n" + " - Total Cards: " + Arrays.stream(deck).filter(card -> card != null).count());
        System.out.println("\n" + "~".repeat(34));
    }

    public static void plainlyDisplayDeck(String[] deck) {
        int total_cards = 0 ;
        for (String card : deck) {
            if (card != null) {
                total_cards ++;
                System.out.println(card);
            }
        }
        System.out.println("\n"+ " - Total Cards: " + total_cards);
    }

    public static void displayNumberizedDeck(String[] deck) {
        int total_cards = 0 ;
        for (String card : deck) {
            if (card != null) {
                total_cards ++;
                System.out.println(card);
            }
        }
        System.out.println("\n"+ " - Total Cards: " + total_cards);
    }

    public static void enter_to_continue(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        scanner.nextLine();
        System.out.println();
    }

    public static void shuffleArray(String[] array) {
        Random rand = new Random();

        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);

            // Swap array[i] and array[index]
            String temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }

    public static void findAndDisplayMatchingCards(String[] cards) {
        int totalCards = 0;
        for (int i = 0; i < cards.length - 1; i++) {
            for (int j = i + 1; j < cards.length; j++) {
                if (cards[i] != null && cards[j] != null) { 
                    if (areMatching(cards[i], cards[j])) {
                        System.out.println(cards[i] + " -  " + cards[j]);
                        cards[i]  = null;
                        cards[j] = null;
                        totalCards +=2;
                        break;
                    }
            }
        }
    }
    if (totalCards != 0) {
        System.out.println("\n"+ " - Total: " + totalCards);
    }
    else {
        System.out.println("\n"+ " - No Paired Cards");
    }
    }

    public static boolean areMatching(String card1, String card2) {
        String rank1 = card1.split(" ")[0];
        String rank2 = card2.split(" ")[0];
        return rank1.equals(rank2);
    }

    public static int getDeckLength(String[] deck){
        int length = 0;
        for (String card: deck) {
            if (card != null) {
                length++;
            }
        }
        return length;
    }

    public static boolean isValidInteger(String input, int minValue, int maxValue) {
        try {
            int value = Integer.parseInt(input);
            return value >= minValue && value <= maxValue;
        } catch (NumberFormatException e) {
            // Handle the case when the string is not a valid integer
            return false;
        }
    }

    public static void addElementToArray(String[] array, String element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = element;
                return;
            }
        }
    }

    public static String[] getRemainingCards(String[] deck, int length) {
        String[] remainingCards = new String[length];
        int cardCount = 0;
        for (String card: deck ) {
            if (card != null) {
                remainingCards[cardCount] = card;
                cardCount++;
            }
        }
        return remainingCards;
    }

    public static void removeElementFromArray(String[] array, String targetElement) {
        for (int i=0; i<array.length; i++) {
            if (array[i] != null && array[i].equals(targetElement)) {
                array[i] = null;
                return;
            }
        }
    }

    public static String getLastCard(String[] array) {
        String lastCard = "";
        for (String element: array) {
            if (element != null ){
                lastCard = element;
                break;
            }
        }
        return lastCard;
    }
}
