package th.ac.ku.sci.cs.projectsa;

public class Misc {
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
}
