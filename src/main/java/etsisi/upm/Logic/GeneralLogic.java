package etsisi.upm.Logic;

public class GeneralLogic {

    private final static String SHOW_HELP= "Commands:\n" +
            "\tclient add  \"<name>\" <DNI> <email> <cashId>\n" +
            "\tclient remove  <DNI>\n" +
            "\tclient list\n" +
            "\tcash add  [<id>] \"<name>\" <email>\n" +
            "\tcash remove  <id>\n" +
            "\tcash list\n" +
            "\tcash tickets\n" +
            "\tticket new  [<id>] <cashId> <userId>\n" +
            "\tticket add <ticketId><cashId> <prodId> <amount> [--p<txt> --p<txt>]\n" +
            "\tticket remove  <ticketId><cashId> <prodId>\n" +
            "\tticket print  <ticketId> <cashId> \n" +
            "\tticket list\n" +
            "\tprod add [<id>] \"<name>\" <category> <price> [<maxPers>] \n" +
            "\tprod update <id> NAME|CATEGORY|PRICE <value>\n" +
            "\tprod addFood  [<id>] \"< name>\" <price> <expiration: yyyy-MM-dd> <max_people>\n" +
            "\tprod addMeeting  [<id>] \"<name>\" <price> < expiration: yyyy-MM-dd> <max_people>   \n" +
            "\tprod list\n" +
            "\tprod remove\n" +
            "\thelp\n" +
            "\techo \"<text>\"\n" +
            "\texit\n" +
            "Categories: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS\n" +
            "Discounts if there are ≥2 units in the category: MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%.\n";

    public String commandEcho(String input) {
        String msg =input.substring(4).trim();
        if (!msg.startsWith("\"") || !msg.endsWith("\"")) {
           throw new IllegalArgumentException("message must be in between quotes.");
        } else {
            return msg + "\n";
        }
    }

   public String showHelp(){
       return SHOW_HELP;
   }
}
