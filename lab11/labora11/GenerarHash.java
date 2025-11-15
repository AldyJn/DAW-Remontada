import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "12345";
        String hash = encoder.encode(password);
        System.out.println("Hash para '" + password + "':");
        System.out.println(hash);
    }
}
