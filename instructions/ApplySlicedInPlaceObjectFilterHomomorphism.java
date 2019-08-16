//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
// PLEASE DO NOT EDIT WITHOUT THE EXPLICIT CONSENT OF THE AUTHOR! \\
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

package hlt.language.design.instructions;

/**
 * @version     Last modified on Wed Jun 20 14:29:51 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

import java.util.Iterator;

import hlt.language.design.backend.Runtime;
import hlt.language.design.backend.Sliceable;
import hlt.language.design.backend.Block;

public class ApplySlicedInPlaceObjectFilterHomomorphism extends SlicingInstruction
{
  public ApplySlicedInPlaceObjectFilterHomomorphism (int[][] slices)
    {
      setName("APPLY_IP_SLICED_FHOM_O");
      _slices = slices;
    }

  public final void execute (Runtime r) throws Exception
    {
      Sliceable collection = (Sliceable)r.popObject();
      Object[] slicers = new Object[_slices.length];
      for (int i=0; i<slicers.length; i++)
        slicers[i] = r.popObject();
      Iterator i = collection.sliceIterator(_slices,slicers);

      Block filter   = (Block)r.popObject();
      Block function = (Block)r.popObject();

      PushValueObject pushElement = new PushValueObject();
      Instruction[] code = { pushElement // collection element
                           , new EnterBlock(filter)
                           , Instruction.STOP_ON_FALSE
                           , pushElement
                           , new EnterBlock(function)
                           , Instruction.STOP
                           };
      r.saveState();
      r.setCode(code);

      while (i.hasNext())
        {
          pushElement.setValue(i.next());
          r.resetIP();
          r.run();
        }

      r.restoreState();
    }
}
