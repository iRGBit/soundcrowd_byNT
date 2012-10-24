/**
 * --------------------------------------------------------------------------------
 *  NoiseTube Mobile client (Java implementation)
 *  
 *  Copyright (C) 2008-2010 SONY Computer Science Laboratory Paris
 *  Portions contributed by Vrije Universiteit Brussel (BrusSense team), 2008-2011
 * --------------------------------------------------------------------------------
 *  This library is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU Lesser General Public License, version 2.1, as published
 *  by the Free Software Foundation.
 *  
 *  This library is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 *  details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License along
 *  with this library; if not, write to:
 *    Free Software Foundation, Inc.,
 *    51 Franklin Street, Fifth Floor,
 *    Boston, MA  02110-1301, USA.
 *  
 *  Full GNU LGPL v2.1 text: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt
 *  NoiseTube project source code repository: http://code.google.com/p/noisetube
 * --------------------------------------------------------------------------------
 *  More information:
 *   - NoiseTube project website: http://www.noisetube.net
 *   - Sony Computer Science Laboratory Paris: http://csl.sony.fr
 *   - VUB BrusSense team: http://www.brussense.be
 * --------------------------------------------------------------------------------
 */

package net.noisetube.util;

import java.util.Random;
import java.util.Vector;

/**
 * @author maisonneuve, mstevens
 *
 */
public class Sort
{

	private static final int INSERTIONSORT_THRESHOLD = 7;
	private static final Random random = new Random(System.currentTimeMillis());

	public static void mergeSort(Object[] src, Object[] dest, int low,
			int high, int off)
	{
		int length = high - low;

		if(length < INSERTIONSORT_THRESHOLD)
		{
			for(int i = low; i < high; i++)
				for(int j = i; j > low
						&& ((Comparable) dest[j - 1]).compareTo(dest[j]) > 0; j--)
					arraySwap(dest, j, j - 1);
			return;
		}

		// Recursively sort halves of dest into src
		int destLow = low;
		int destHigh = high;
		high += off;
		int mid = (low + high) >>> 1;
		mergeSort(dest, src, low, mid, -off);
		mergeSort(dest, src, mid, high, -off);

		// If list is already sorted, just copy from src to dest. This is an
		// optimization that results in faster sorts for nearly ordered lists.
		if(((Comparable) src[mid - 1]).compareTo(src[mid]) <= 0)
		{
			System.arraycopy(src, low, dest, destLow, length);
			return;
		}

		// Merge sorted halves (now in src) into dest
		for(int i = destLow, p = low, q = mid; i < destHigh; i++)
		{
			if(q >= high || p < mid
					&& ((Comparable) src[p]).compareTo(src[q]) <= 0)
				dest[i] = src[p++];
			else
				dest[i] = src[q++];
		}
	}

	private static void arraySwap(Object[] x, int a, int b)
	{
		Object t = x[a];
		x[a] = x[b];
		x[b] = t;
	}

	public synchronized static void quickSort(Vector src)
	{
		quickSortPartion(src, 0, src.size() - 1);
	}

	private static void quickSortPartion(Vector vector, int begin, int end)
	{
		if(end > begin)
		{
			int index = partition(vector, begin, end);
			quickSortPartion(vector, begin, index - 1);
			quickSortPartion(vector, index + 1, end);
		}
	}

	private static int partition(Vector vector, int begin, int end)
	{
		int index = begin + random.nextInt(end - begin + 1);
		Comparable pivot = (Comparable) vector.elementAt(index);
		vectorSwap(vector, index, end);
		for(int i = index = begin; i < end; ++i)
		{
			if(((Comparable) vector.elementAt(i)).compareTo(pivot) <= 0)
			{
				vectorSwap(vector, index++, i);
			}
		}
		vectorSwap(vector, index, end);
		return(index);
	}

	private static void vectorSwap(Vector src, int i, int j)
	{
		Object tmp = src.elementAt(i);
		src.setElementAt(src.elementAt(j), i);
		src.setElementAt(tmp, j);
	}

}
