package krzysztofk.video.rental;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class VideoRentalApplication extends Application<VideoRentalConfiguration> {

  public static void main(String[] args) throws Exception {
    new VideoRentalApplication().run(args);
  }

  public void run(VideoRentalConfiguration videoRentalConfiguration, Environment environment) throws Exception {

  }
}