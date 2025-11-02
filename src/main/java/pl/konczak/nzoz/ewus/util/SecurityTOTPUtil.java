package pl.konczak.nzoz.ewus.util;

import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.time.Instant;

public final class SecurityTOTPUtil {

    /**
     * Generate TOTP
     *
     * @param base32Secret  secret in Base32 (case-insensitive, no spaces)
     * @param hmacAlgorithm one of "HmacSHA1", "HmacSHA256", "HmacSHA512"
     * @param digits        number of code digits (e.g. 6)
     * @param periodSeconds time step in seconds (e.g. 30)
     * @return numeric OTP as zero-padded string
     */
    public static String generateTOTP(String base32Secret, String hmacAlgorithm, int digits, int periodSeconds) {
        if (base32Secret == null) throw new IllegalArgumentException("Secret is null");
        Base32 base32 = new Base32();
        byte[] key = base32.decode(base32Secret.replace(" ", "").toUpperCase());

        long now = Instant.now().getEpochSecond();
        long counter = now / periodSeconds;

        try {
            // 8-byte big-endian counter
            byte[] counterBytes = ByteBuffer.allocate(8).putLong(counter).array();

            Mac mac = Mac.getInstance(hmacAlgorithm);
            SecretKeySpec keySpec = new SecretKeySpec(key, hmacAlgorithm);
            mac.init(keySpec);
            byte[] hash = mac.doFinal(counterBytes);

            // dynamic truncation
            int offset = hash[hash.length - 1] & 0x0f;
            int binary =
                    ((hash[offset] & 0x7f) << 24) |
                            ((hash[offset + 1] & 0xff) << 16) |
                            ((hash[offset + 2] & 0xff) << 8) |
                            (hash[offset + 3] & 0xff);

            int otp = binary % (int) Math.pow(10, digits);
            return String.format("%0" + digits + "d", otp);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate TOTP", e);
        }
    }
}

