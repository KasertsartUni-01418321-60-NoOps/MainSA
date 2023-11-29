package th.ac.ku.sci.cs.projectsa;

public class Misc {
    public static final String[] ThaiStr_DataSpec_Status_SR = { "รอการนัดตรวจสอบสภาพฯ", "ปฏิเสธการรับซื้อแล้ว",
            "รับซื้อแล้ว" };
    public static final String[] ThaiStr_DataSpec_Status_pd = { "รอการประกาศขาย", "พร้อมขาย", "ขายแล้ว/กำลังส่ง",
            "ส่งแล้ว" };
    public static final double choosenDefaultValueFor_PaidAmount_AtCheckItemPAge = 25000;
    public static final double choosenDefaultValueFor_PaidAmount_AtAddItemPAge = 30000;
    public static final double choosenDefaultValueFor_TPrice_AtQuotationPAge = 1000;
    public static final double choosenStepValueFor_PaidAmount_AtCheckItemPAge = 100;
    public static final double choosenStepValueFor_PaidAmount_AtAddItemPAge = 100;
    public static final double choosenStepValueFor_TPrice_AtQuotationPAge = 100;
    public static final int[][] javafxListViewCellRowBgColorBasedOnPdType= new int[][] {
        {255, 255, 224},
        {240, 128, 128},
        {0, 73, 62},
        {32, 178, 170},
        {173, 216, 230  },
        {255, 255, 224},
        {144, 238, 144}
    };

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
            retval[i] = charArray[new java.util.Random().nextInt(charArray.length)];
        }
        return new String(retval);
    }

    private double getHighestContrastFGColor_helper2(int[] color) {
        double r = color[0];
        double g = color[1];
        double b = color[2];
        r = r <= 10 ? r / 255.0 : Math.pow(((r / 255.0 + 0.055) / 1.055), 2.4);
        g = g <= 10 ? g / 255.0 : Math.pow(((g / 255.0 + 0.055) / 1.055), 2.4);
        b = b <= 10 ? b / 255.0 : Math.pow(((b / 255.0 + 0.055) / 1.055), 2.4);
        return 0.2126 * r + 0.7152 * g + 0.0722 * b;
    }
    
    private double getHighestContrastFGColor_helper1(int[] background, int[] foreground) {
        double luminance_bg = getHighestContrastFGColor_helper2(background);
        double luminance_fg = getHighestContrastFGColor_helper2(foreground);
        return (Math.max(luminance_bg, luminance_fg) + 0.05) / (Math.min(luminance_bg, luminance_fg) + 0.05);
    }
    
    public int[] getHighestContrastFGColor(int[] rgb) {
        int[] white = new int[] {255, 255, 255};
        int[] black = new int[] {0, 0, 0};
        double contrast_with_white = getHighestContrastFGColor_helper1(rgb, white);
        double contrast_with_black = getHighestContrastFGColor_helper1(rgb, black);
        return contrast_with_white > contrast_with_black  ? white : black;
    }

    public static class ListViewRowDataWrapper<T> {
        public final T ref;
        public final String repr;
        public final Object[] params;

        public ListViewRowDataWrapper(T ref, String repr, Object[] params) {
            this.ref = ref;
            this.repr = repr;
            this.params=params;
        }

        public ListViewRowDataWrapper(T ref, String repr) {
            this(ref,repr,null);
        }

        @Override
        public String toString() {
            return this.repr;
        }
    }
}
