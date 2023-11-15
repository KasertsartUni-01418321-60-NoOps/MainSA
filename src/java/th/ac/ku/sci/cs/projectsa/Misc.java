package th.ac.ku.sci.cs.projectsa;

public class Misc {
    public static final String[] ThaiStr_DataSpec_Status_SR= {"รอการนัดเช็คสภาพฯ","ปฏิเสธการรับซื้อแล้ว","รับซื้อแล้ว"};

    public static String[] rickrollLyrics = new String[] {
        "We're no strangers to love",
        "You know the rules and so do I (do I)",
        "A full commitment's what I'm thinking of",
        "You wouldn't get this from any other guy",
        "I just wanna tell you how I'm feeling",
        "Gotta make you understand",
        "Never gonna give you up",
        "Never gonna let you down",
        "Never gonna run around and desert you",
        "Never gonna make you cry",
        "Never gonna say goodbye",
        "Never gonna tell a lie and hurt you",
        "We've known each other for so long",
        "Your heart's been aching, but you're too shy to say it (say it)",
        "Inside, we both know what's been going on (going on)",
        "We know the game and we're gonna play it",
        "And if you ask me how I'm feeling",
        "Don't tell me you're too blind to see",
        "Never gonna give you up",
        "Never gonna let you down",
        "Never gonna run around and desert you",
        "Never gonna make you cry",
        "Never gonna say goodbye",
        "Never gonna tell a lie and hurt you",
        "Never gonna give you up",
        "Never gonna let you down",
        "Never gonna run around and desert you",
        "Never gonna make you cry",
        "Never gonna say goodbye",
        "Never gonna tell a lie and hurt you",
        "We've known each other for so long",
        "Your heart's been aching, but you're too shy to say it (to say it)",
        "Inside, we both know what's been going on (going on)",
        "We know the game and we're gonna play it",
        "I just wanna tell you how I'm feeling",
        "Gotta make you understand",
        "Never gonna give you up",
        "Never gonna let you down",
        "Never gonna run around and desert you",
        "Never gonna make you cry",
        "Never gonna say goodbye",
        "Never gonna tell a lie and hurt you",
        "Never gonna give you up",
        "Never gonna let you down",
        "Never gonna run around and desert you",
        "Never gonna make you cry",
        "Never gonna say goodbye",
        "Never gonna tell a lie and hurt you",
        "Never gonna give you up",
        "Never gonna let you down",
        "Never gonna run around and desert you",
        "Never gonna make you cry",
        "Never gonna say goodbye",
        "Never gonna tell a lie and hurt you",
    };

        // entire exception handling info: mode=no
        public static String getISODateTimeString() {
            return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        
	// entire exception handling info: mode=no
	public static String passwordHash(String password) throws java.security.NoSuchAlgorithmException {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            
            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw e; // it should not raise it unless poor JRE lamo
        }		
	}
    
    public static String generateRandomID() {
        char[] charArray = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };
        char[] retval = new char[8];
        for (int i = 0; i < 8; i++) {
            retval[i]=charArray[new java.util.Random().nextInt(charArray.length)];
        }
        return new String(retval);
    }
    public static class ListViewRowDataWrapper<T> {
        public final T ref;
        public final String repr;
        public ListViewRowDataWrapper(T ref, String repr) {
            this.ref = ref;
            this.repr = repr;
        }
        @Override
        public String toString() {
            return this.repr;
        }
    }
}
