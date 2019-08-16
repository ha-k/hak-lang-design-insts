//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
// PLEASE DO NOT EDIT WITHOUT THE EXPLICIT CONSENT OF THE AUTHOR! \\
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

package hlt.language.design.instructions;

/**
 * @version     Last modified on Wed Jun 20 14:29:51 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

import hlt.language.util.DoubleIterator;

import hlt.language.design.backend.Runtime;
import hlt.language.design.backend.Iteratable;
import hlt.language.design.backend.Block;

public class ApplyInPlaceRealCollectionHomomorphism extends Instruction
{
  public  ApplyInPlaceRealCollectionHomomorphism ()
    {
      setName("APPLY_IP_COLL_HOM_R");
    }

  public final void execute (Runtime r) throws Exception
    {
      Iteratable collection = (Iteratable)r.popObject();
      Block function  = (Block)r.popObject();

      r.saveState();

      Instruction[] code = { new PushValueReal() // collection element
                           , new EnterBlock(function)
                           , Instruction.STOP
                           };

      r.setCode(code);

      DoubleIterator i = collection.realIterator(true);
      while (i.hasNext())
        {
          ((PushValueReal)code[0]).setValue(i.next());
          r.resetIP();
          r.run();
        }

      r.restoreState();
    }
}
