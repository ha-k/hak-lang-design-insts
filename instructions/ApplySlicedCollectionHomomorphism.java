//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
// PLEASE DO NOT EDIT WITHOUT THE EXPLICIT CONSENT OF THE AUTHOR! \\
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

package hlt.language.design.instructions;

/**
 * @version     Last modified on Wed Jun 20 14:29:51 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

import hlt.language.tools.Misc;

abstract public class ApplySlicedCollectionHomomorphism extends SlicingInstruction
{
  protected Instruction _tally;

  protected  ApplySlicedCollectionHomomorphism (int[][] slices, Instruction tally)
    {
      _slices = slices;
      _tally = tally;
    }

  public final String toString ()
    {
      return name() + "(" + Misc.arrayToString(_slices) + "," + _tally + ")";
    }
}
