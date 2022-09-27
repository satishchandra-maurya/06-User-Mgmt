package in.satish.props;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "user-api")
@Data
public class AppProperties {

	private Map<String, String> messages = new HashedMap<>();
}
