package flowengine.action.impl;

import flowengine.action.Action;
import flowengine.context.FlowContext;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import org.springframework.stereotype.Component;
import world.World;
import world.context.Context;
import world.model.Space;

/**
 * PetTraverseAction.
 * 
 * @author anbang
 * @date 2023-03-16 21:33
 */
@Component(value = "petTraverseAction")
public class PetTraverseAction implements Action {

  private final Stack<Space> stack = new Stack<>();

  private final Set<Space> visited = new HashSet<>();

  @Override
  public void execute(FlowContext context) {
    Context ctx = context.getContext();
    if (this.visited.size() == ctx.getSpaces().size()) {
      this.visited.clear();
    }
    Space space = World.getSpace(ctx, ctx.getPet().getSpaceIndex());
    if (this.stack.isEmpty()) {
      this.stack.push(space);
      this.visited.add(space);
    } else if (this.stack.peek() != space) {
      this.stack.pop();
      this.stack.push(space);
      this.visited.add(space);
    }

    Space current = this.stack.pop();
    Set<Space> neighbors = new HashSet<>(ctx.getNeighborMap().get(current));
    neighbors.removeAll(this.visited);
    for (Space s : neighbors) {
      if (!this.stack.contains(s)) {
        this.stack.push(s);
      }
    }

    ctx.getPet().setSpaceIndex(this.stack.peek().getOrder());
    this.visited.add(this.stack.peek());
  }

}
