package fi.raka.test.utils;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

public class RobolectricMavenTestRunner extends RobolectricTestRunner {

  public RobolectricMavenTestRunner(Class<?> testClass) throws InitializationError {
      super(testClass);
  }

  @Override
  protected AndroidManifest getAppManifest(Config config) {
	  return new AndroidManifest(Fs.fileFromPath("../coffeebuddy"));
	  /*
      String manifestProperty = System.getProperty("android.manifest");
      if (config.manifest().equals(Config.DEFAULT) && manifestProperty != null) {
          String resProperty = System.getProperty("android.resources");
          String assetsProperty = System.getProperty("android.assets");
          return new AndroidManifest(Fs.fileFromPath(manifestProperty), Fs.fileFromPath(resProperty),
                  Fs.fileFromPath(assetsProperty));
      }
      AndroidManifest appManifest = super.getAppManifest(config);
      return appManifest;
      */
  }

}
