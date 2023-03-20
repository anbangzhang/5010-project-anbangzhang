package flowengine.action.impl;

import flowengine.action.Action;
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
  public void execute(Context context) {
    if (this.visited.size() == context.getSpaces().size()) {
      this.visited.clear();
    }
    Space space = World.getSpace(context, context.getPet().getSpaceIndex());
    if (this.stack.isEmpty()) {
      this.stack.push(space);
      this.visited.add(space);
    } else if (this.stack.peek() != space) {
      this.stack.pop();
      this.stack.push(space);
      this.visited.add(space);
    }

    Space current = this.stack.pop();
    Set<Space> neighbors = new HashSet<>(context.getNeighborMap().get(current));
    neighbors.removeAll(this.visited);
    for (Space s : neighbors) {
      if (!this.stack.contains(s)) {
        this.stack.push(s);
      }
    }

    context.getPet().setSpaceIndex(this.stack.peek().getOrder());
    this.visited.add(this.stack.peek());
  }

}
