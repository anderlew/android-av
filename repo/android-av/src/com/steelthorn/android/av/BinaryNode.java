/*******************************************************************************
 * Copyright (c) 2013 Jeff Mixon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * (or any later version, at your option)  which accompanies this distribution,
 * and is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Jeff - initial API and implementation
 ******************************************************************************/
package com.steelthorn.android.av;

//Basic node stored in unbalanced binary search trees
//Note that this class is not accessible outside
//of this package.

class BinaryNode<T extends Comparable<T>>
{
	// Constructors
	BinaryNode(T theElement)
	{
		element = theElement;
		left = right = null;
	}

	// Friendly data; accessible by other package routines
	T element; // The data in the node
	BinaryNode<T> left; // Left child
	BinaryNode<T> right; // Right child
}
