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
import hlt.language.design.backend.ObjectMap;
import hlt.language.design.backend.Indexable;

public class FillArrayFromObjectMap extends Instruction implements PushValue, PushObject
{
  public FillArrayFromObjectMap ()
    {
      setName("FILL_ARRAY_OM");
    }

  public final void execute (Runtime r)
    {
      Object[] array = new Object[r.popInt()];
      ObjectMap objectMap = (ObjectMap)(array[0] = r.popObject());
      Object[] seed = objectMap.array();
      Indexable indexable = (Indexable)(objectMap.indexable());
      for (int i=1; i<array.length; i++)
        array[i] = new ObjectMap(r.copy(seed),indexable);
      r.pushObject(array);
      r.incIP();
    }
}
