//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
// PLEASE DO NOT EDIT WITHOUT THE EXPLICIT CONSENT OF THE AUTHOR! \\
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

package hlt.language.design.instructions;

/**
 * @version     Last modified on Wed Jun 20 14:29:51 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

import hlt.language.design.backend.Runtime;
import hlt.language.design.backend.Indexable;
import hlt.language.design.backend.RuntimeInt;
import hlt.language.design.backend.SizeMatchException;

public class CheckArraySize extends Instruction
{
  public CheckArraySize ()
    {
      setName("CHECK_ARRAY_SIZE");
    }

  public final void execute (Runtime r) throws SizeMatchException
    {
      int actualSize = r.popInt();
      int neededSize = 0;

      if (r.objectResult() instanceof RuntimeInt)
        neededSize = ((RuntimeInt)r.popObject()).value();
      else
        {
          neededSize = ((Indexable)r.objectResult()).size();      
          r.pushObject(null);   // dummy permutation array
        }

      if (actualSize != neededSize)
        throw new SizeMatchException("can't initialize an array of size "+neededSize+
                                     " with an array of size "+actualSize);

      r.incIP();
    }
}
