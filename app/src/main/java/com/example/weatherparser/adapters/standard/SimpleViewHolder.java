package com.example.weatherparser.adapters.standard;

import android.view.View;

/**
 * Date: 08.09.16
 * Time: 19:30
 *
 * @author Olga
 */
public abstract class SimpleViewHolder<ModelType>
{
	public View rootView;

	protected SimpleViewHolder(View rootView)
	{
		this.rootView = rootView;
		create(rootView);
	}

	final public View getRoot()
	{
		return rootView;
	}

	abstract protected void create(View rootView);

	abstract public void bind(ModelType model);
}