package flowengine.enricher.impl;

import flowengine.context.FlowContext;
import flowengine.enricher.Enricher;
import flowengine.enums.Flow;
import flowengine.request.BaseRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import model.World;
import model.base.BaseWeapon;
import model.constant.Constants;
import model.context.Context;
import org.springframework.stereotype.Component;

/**
 * InputEnricher.
 * 
 * @author anbang
 * @date 2023-03-18 07:58
 */
@Component(value = "inputEnricher")
public class InputEnricher implements Enricher {

  @Override
  public void enrich(FlowContext context) throws IOException {
    BaseRequest request = context.getRequest();
    String flowDesc = context.getFlow();
    Context ctx = context.getContext();
    Flow flow = Flow.getByDesc(flowDesc);
    Scanner scanner = (Scanner) ctx.get(Constants.SCANNER);
    Appendable out = (Appendable) ctx.get(Constants.OUT);

    if (Objects.isNull(request.getInput())) {

      if (!request.getSkipInput()) {

        switch (flow) {
          case MOVE_PLAYER:
            out.append(String.format("Please input a neighbor space name from the neighbors: %s\n",
                World.getNeighbors(ctx, request.getPlayer().getSpaceIndex())));
            break;
          case PICK_UP_WEAPON:
            out.append(String.format("Please input a weapon name from the weapons: %s\n",
                World.getSpace(ctx, request.getPlayer().getSpaceIndex()).getWeapons().stream()
                    .map(BaseWeapon::getName).collect(Collectors.toList())));
            break;
          case MOVE_PET:
            out.append(String.format("Please input a space name from the spaces: %s\n",
                World.getAllSpaces(ctx)));
            break;
          default:
            break;
        }

        request.setInput(scanner.nextLine().trim());

      } else {
        request.setInput(Constants.COMPUTER_DEFAULT);
      }
    }

  }

}
