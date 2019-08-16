//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
// PLEASE DO NOT EDIT WITHOUT THE EXPLICIT CONSENT OF THE AUTHOR! \\
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

package hlt.language.design.instructions;

/**
 * @version     Last modified on Wed Jun 20 14:29:51 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

import java.lang.reflect.Array;
import hlt.language.design.backend.Runtime;
import hlt.language.design.backend.NullValueException;

public class ArraySize extends Instruction
{
  public ArraySize ()
    {
      setName("ARRAY_SIZE");
    }

  public final void execute (Runtime r) throws NullValueException
    {
      r.pushInt(Array.getLength(r.popObject("can't size a null array")));
      r.incIP();
    }
}
