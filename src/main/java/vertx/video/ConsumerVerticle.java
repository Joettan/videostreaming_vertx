package vertx.video;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;


public class ConsumerVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception{
      vertx.eventBus().consumer("testt", message -> {
//        System.out.println("I have received a message: " + message.body());
        logger.info("I have received a message: " + message.body());
      });

//      vertx.setPeriodic(1000, (a)->{
//        System.out.println("ConsumerVerticle timer fired!");
//      });
  }

}
