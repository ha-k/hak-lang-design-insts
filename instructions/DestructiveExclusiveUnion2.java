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
import hlt.language.design.backend.RuntimeSet;

public class DestructiveExclusiveUnion2 extends Instruction
{
  public DestructiveExclusiveUnion2 ()
    {
      setName("D_XNION_2");
    }

  /**
   * <b>NB</b>: the <i>second</i> argument is the one modified.
   */
  public final void execute (Runtime r)
    {
      RuntimeSet set = (RuntimeSet)r.popObject();
      ((RuntimeSet)r.objectResult()).exclusion(set);
      r.incIP();
    }
}
