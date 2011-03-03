/*
 * Copyright (C) 2010 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.portal.mop.navigation;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface Scope
{

   /**
    * A scope that prunes the tree, it keeps all nodes below a specific depth.
    */
   public static class Pruning implements Scope
   {

      /** . */
      private final Visitor visitor;

      /**
       * Creates a new pruning scope.
       *
       * @param height the max height of the pruned tree
       * @throws IllegalArgumentException if the height is negative
       */
      public Pruning(final int height) throws IllegalArgumentException
      {
         if (height < 0)
         {
            throw new IllegalArgumentException("Cannot provide negative height");
         }
         this.visitor = new Visitor()
         {
            public VisitMode visit(int depth, NodeData data)
            {
               return depth < height ? VisitMode.CHILDREN : VisitMode.NODE;
            }
         };
      }

      public Visitor get()
      {
         return visitor;
      }
   }

   Scope SINGLE = new Pruning(0);

   Scope CHILDREN = new Pruning(1);

   Scope GRANDCHILDREN = new Pruning(2);

   Scope ALL = new Scope()
   {
      private Visitor instance = new Visitor()
      {
         public VisitMode visit(int depth, NodeData data)
         {
            return VisitMode.CHILDREN;
         }
      };
      public Visitor get()
      {
         return instance;
      }
   };

   Visitor get();

   public interface Visitor
   {
      VisitMode visit(int depth, NodeData data);
   }
}