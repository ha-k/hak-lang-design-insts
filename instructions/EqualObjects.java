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

public class EqualObjects extends Instruction
{
  public EqualObjects ()
    {
      setName("EQU_OO");
    }

  public final void execute (Runtime r)
    {
      Object o1 = r.popObject();
      Object o2 = r.popObject();

      r.pushBoolean(o1 == null ? o2 == null : o1.equals(o2));
      r.incIP();
    }
}
