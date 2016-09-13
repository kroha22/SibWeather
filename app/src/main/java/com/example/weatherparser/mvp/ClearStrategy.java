package com.example.weatherparser.mvp;

import android.text.TextUtils;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

import java.util.Iterator;
import java.util.List;


/**
 * Date: 09.09.16
 * Time: 13:29
 *
 * Strategy clear all commands from stack with equals tag.
 *
 * @author Olga
 */
public class ClearStrategy implements StateStrategy
{
	@Override
	public <View extends MvpView> void beforeApply(List<ViewCommand<View>> list, ViewCommand<View> pair)
	{
		final Iterator<ViewCommand<View>> it = list.iterator();
		while (it.hasNext())
		{
			if (TextUtils.equals(it.next().getTag(), pair.getTag()))
			{
				it.remove();
			}
		}

		list.add(pair);
	}

	@Override
	public <View extends MvpView> void afterApply(List<ViewCommand<View>> list, ViewCommand<View> pair)
	{
		// stub
	}
}
