package vertx.video;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.setProperty("java.util.logging.config.file", MainVerticle.class.getResource("/logging.properties").getFile());
    vertx.createHttpServer().requestHandler(req -> {
      //Different approach to write response
      req.response()
        .putHeader("content-type", "text/plain").write("Hello from Vert.x! in docker").onComplete(ar -> {
          if (ar.succeeded()) {
            System.out.println("success");
          } else {
            System.out.println("fail");
          }
        });
      //        .end("Hello from Vert.x! in docker");
//
//      req.response()
//        .putHeader("content-type", "text/plain").end("Hello from Vert.x! in docker");
    }).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });

//    vertx.setPeriodic(5000, id -> {
//      System.out.println("timer fired!");
//    });

    vertx.setPeriodic(1000, (a)->{
      vertx.eventBus().send("testt", "hello"+Math.random());
      logger.info("send message to testt");
    });

    vertx.deployVerticle("vertx.video.ConsumerVerticle");
  }

  public static void main(String[] args) {
    logger.info("MainVerticle.main");
  }

}
