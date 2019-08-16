//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
// PLEASE DO NOT EDIT WITHOUT THE EXPLICIT CONSENT OF THE AUTHOR! \\
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

package hlt.language.design.instructions;

/**
 * @version     Last modified on Wed Jun 20 14:29:51 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 * @copyright   &copy; 2000-2002 <a href="http://www.hlt.fr/">HLT, S.A.</a>
 */

public class DummyNextCircOffSet extends Instruction
{
  public DummyNextCircOffSet ()
    {
      setName("DUMMY_NEXT_C_OFFSET");
      dummify();
    }
}
