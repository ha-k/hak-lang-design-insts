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

public class ApplySlicedInPlaceObjectCollectionHomomorphism extends SlicingInstruction
{
  public ApplySlicedInPlaceObjectCollectionHomomorphism (int[][] slices)
    {
      setName("APPLY_IP_COLL_SLICED_HOM_O");
      _slices = slices;
    }

  public final void execute (Runtime r) throws Exception
    {
      Sliceable collection = (Sliceable)r.popObject();
      Object[] slicers = new Object[_slices.length];
      for (int i=0; i<slicers.length; i++)
        slicers[i] = r.popObject();
      Iterator i = collection.sliceIterator(_slices,slicers);

      Block function  = (Block)r.popObject();

      Instruction[] code = { new PushValueObject() // collection element
                           , new EnterBlock(function)
                           , Instruction.STOP
                           };

      r.saveState();
      r.setCode(code);

      while (i.hasNext())
        {
          ((PushValueObject)code[0]).setValue(i.next());
          r.resetIP();
          r.run();
        }

      r.restoreState();
    }
}
