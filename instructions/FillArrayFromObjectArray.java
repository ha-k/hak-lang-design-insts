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

public class FillArrayFromObjectArray extends Instruction implements PushValue, PushObject
{
  public FillArrayFromObjectArray ()
    {
      setName("FILL_ARRAY_OA");
    }

  public final void execute (Runtime r)
    {
      Object[] array = new Object[r.popInt()];
      Object[] seed = (Object[])(array[0] = r.popObject());
      for (int i=1; i<array.length; i++)
        array[i] = r.copy(seed);
      r.pushObject(array);
      r.incIP();
    }
}
