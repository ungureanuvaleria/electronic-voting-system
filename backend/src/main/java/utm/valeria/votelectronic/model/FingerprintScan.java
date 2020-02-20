package utm.valeria.votelectronic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FingerprintScan {

    @JsonProperty("fingerprint_id")
    private String fingerprintId;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("correctness")
    private int correctness;
 }
