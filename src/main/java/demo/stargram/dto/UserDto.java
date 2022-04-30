package demo.stargram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class UserDto {

    @JsonProperty
    private String token;

    @JsonProperty
    private String name;

}
