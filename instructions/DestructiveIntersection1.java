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

public class DestructiveIntersection1 extends Instruction
{
  public DestructiveIntersection1 ()
    {
      setName("D_INTER_1");
    }

  /**
   * <b>NB</b>: the <i>first</i> argument is the one modified.
   */
  public final void execute (Runtime r)
    {
      RuntimeSet set = (RuntimeSet)r.popObject();
      r.pushObject(set.intersection((RuntimeSet)r.popObject()));
      r.incIP();
    }
}
