package animation;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class Fade {

    private final FadeTransition ft;

//    public Fade(Button button, double fromValue, double toValue, int durationInMilliseconds) {
//        ft = new FadeTransition(Duration.millis(durationInMilliseconds), button);
//        ft.setFromValue(fromValue);
//        ft.setToValue(toValue);
//    }
    public Fade(Node node){
        ft = new FadeTransition(Duration.millis(3000), node);
        ft.setFromValue(1);
        ft.setToValue(0);
    }

    public void play() {
        ft.play();
    }
}
