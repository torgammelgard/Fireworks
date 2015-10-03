import java.util.EventListener;

/**
 * Created by Tor on 2015-10-02.
 *
 * When a ball has faded away completely (alpha = 0) ballFadedOut gets fired
 * and listeners can handle this by implementing this interface
 */

public interface IBallListener extends EventListener{
    void ballFadedOut(FadingBall ball);
}
