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
import hlt.language.design.backend.*;
import hlt.language.design.backend.Runtime;

public class ArrayInitialize extends Instruction
{
  public ArrayInitialize ()
    {
      setName("ARRAY_INITIALIZE");
    }

  public final void execute (Runtime r)
    throws NullValueException, SizeMatchException, ArrayAllocationErrorException
    {
      int extensionDepth = r.popInt();
      int dimension = r.popInt();
      Object[] indexables = new Object[dimension];
      for (int i=0; i<dimension; i++)
        indexables[i] = r.popObject("can't allocate a map with a null indexable");
      Object arrayObject = r.objectResult();
      Object checkedArrayObject = _checkArray(indexables,arrayObject,0,extensionDepth);
      if (arrayObject != checkedArrayObject)
        {
          r.popObject();
          r.pushObject(checkedArrayObject);
        }
                             
      r.incIP();
    }

  private Object _checkArray(Object[] indexables, Object object, int depth, int extensionDepth)
    {
      if (depth >= extensionDepth)
        return _checkNonExtensionalArray(indexables,object,depth);

      if (object == null)
        return object;

      if (depth == indexables.length)
        return object;

      if (indexables[depth] instanceof RuntimeInt)      // necessarily a native array
        {
          int neededSize = ((RuntimeInt)indexables[depth]).value();
          int actualSize = Array.getLength(object);

          if (neededSize != actualSize)
            throw new SizeMatchException("can't initialize an array of size "+neededSize+
                                         " with an array of size "+actualSize);

          depth = depth+1;
          if (!(object instanceof int[] || object instanceof double[]))
            {
              Object[] array = (Object[])object;
              for (int i=neededSize; i-->0;)
                array[i] = _checkArray(indexables,array[i],depth,extensionDepth);
            }

          return object;
        }

      Indexable indexable = (Indexable)indexables[depth];

      RuntimeMap map = ((RuntimeMap)object);    // necessarily a map
      map.trimToSize();                         // just in case...

      if (!indexable.equals(map.indexable()))
        throw new ArrayAllocationErrorException
          ("can't initialize a map from one with a different index set");

      if (!(map instanceof IntMap || map instanceof RealMap))
        {
          Object[] array = (Object[])map.extractArray();
          depth = depth+1;
          for (int i=indexable.size(); i-->0;)
            array[i] = _checkArray(indexables,array[i],depth,extensionDepth);
        }

      return map;
    }

  private Object _checkNonExtensionalArray(Object[] indexables, Object object, int depth)
    {
      if (object == null)
        return object;

      if (depth == indexables.length)
        return object;

      if (indexables[depth] instanceof RuntimeInt)      // necessarily a native array
        {
          int neededSize = ((RuntimeInt)indexables[depth]).value();
          int actualSize = Array.getLength(object);

          if (neededSize != actualSize)
            throw new SizeMatchException("can't initialize an array of size "+neededSize+
                                         " with an array of size "+actualSize);

          depth = depth+1;
          if (!(object instanceof int[] || object instanceof double[]))
            {
              Object[] array = (Object[])object;
              for (int i=neededSize; i-->0;)
                array[i] = _checkNonExtensionalArray(indexables,array[i],depth);
            }

          return object;
        }

      Indexable indexable = (Indexable)indexables[depth];

      if (!(object instanceof RuntimeMap))              // must make a map out of a native array
        {          
          int neededSize = indexable.size();
          int actualSize = Array.getLength(object);

          if (neededSize != actualSize)
            throw new SizeMatchException("can't initialize a map of size "+neededSize+
                                         " with an array of size "+actualSize);

          depth = depth+1;
          if (!(object instanceof int[] || object instanceof double[]))
            {
              Object[] array = (Object[])object;
              for (int i=neededSize; i-->0;)
                array[i] = _checkNonExtensionalArray(indexables,array[i],depth);
            }

          if (object instanceof int[])
            return new IntMap((int[])object,indexable);

          if (object instanceof double[])
            return new RealMap((double[])object,indexable);

          return new ObjectMap((Object[])object,indexable);
        }

      RuntimeMap map = ((RuntimeMap)object);    // necessarily a map
      map.trimToSize();                         // just in case...

      int[] permutation = new int[indexable.size()]; 

      if (!indexable.equals(map.indexable(),permutation))
        throw new ArrayAllocationErrorException
          ("can't initialize a map from one with a different index set");

      if (!(map instanceof IntMap || map instanceof RealMap))
        {
          Object[] array = (Object[])map.extractArray();
          depth = depth+1;
          for (int i=indexable.size(); i-->0;)
            array[i] = _checkNonExtensionalArray(indexables,array[i],depth);
        }

      if (map instanceof IntMap)
        return new IntMap(_permute((int[])map.extractArray(),permutation),indexable);
      else
        if (map instanceof RealMap)
          return new RealMap(_permute((double[])map.extractArray(),permutation),indexable);
        else
          return new ObjectMap(_permute((Object[])map.extractArray(),permutation),indexable);
    }

  private final int[] _permute (int[] array, int[] permutation)
    {
      if (_isTrivial(permutation))
        return array;

      int[] newArray = new int[array.length];

      for (int i=array.length; i-->0;)
        newArray[permutation[i]] = array[i];

      return newArray;
    }

  private final double[] _permute (double[] array, int[] permutation)
    {
      if (_isTrivial(permutation))
        return array;

      double[] newArray = new double[array.length];

      for (int i=array.length; i-->0;)
        newArray[permutation[i]] = array[i];

      return newArray;
    }

  private final Object[] _permute (Object[] array, int[] permutation)
    {
      if (_isTrivial(permutation))
        return array;

      Object[] newArray = new Object[array.length];

      for (int i=array.length; i-->0;)
        newArray[permutation[i]] = array[i];

      return newArray;
    }

  private final boolean _isTrivial (int[] permutation)
    {
      for (int i=permutation.length; i-->0;)
        if (i != permutation[i]) return false;

      return true;
    }
}




