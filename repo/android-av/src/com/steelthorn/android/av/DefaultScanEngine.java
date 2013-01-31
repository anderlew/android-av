package com.steelthorn.android.av;

import java.util.Arrays;
import java.util.List;

class DefaultScanEngine extends ScanEngine
{

	public ScanResult scan(ScanContext ctx)
	{
		ScanResult result = new ScanResult();

		//TODO: Possibly move this to the ScanContext
		int totalCount = 0;
		for (ITargetSource source : ctx.getSources())
		{
			totalCount += source.getTargetCount();
		}
		
		int progressCount = 0;
		for (ITargetSource source : ctx.getSources())
		{
			if (ctx.getListener() != null)
				ctx.getListener().onTargetSourceSwitch(source);

			for (IScanTarget target : source)
			{
				if (ctx.getListener() != null)
					ctx.getListener().onTargetScanBegin(target);

				ThreatInfo ti = scanTarget(target);
				
				progressCount++;
				
				if (ctx.getListener() != null)
					ctx.getListener().onScanProgress(progressCount / totalCount);

				if (ti != null)
					result.addMatchFound(ti);

				if (ctx.getListener() != null)
					ctx.getListener().onTargetScanComplete(target, ti);
			}
		}

		return result;
	}

	public ThreatInfo scanTarget(IScanTarget target)
	{
		
		// O(n) scanning algorithm for now
		// TODO: BST engine based on size
		List<IScanDefinition> defs = Util.getDevDefinitions();

		for (IScanDefinition def : defs)
		{
			if ((def.getDefinitionType() == target.getTargetType()))
			{
				int confidence = 0;
				for (IScanDefinitionCriteria crit : def.getCriterion())
				{
					if (Arrays.equals(crit.getHashValue(), target.getHashValue()))
						confidence += crit.getMatchWeight();
				}
				
				if (confidence > 0)
					return new ThreatInfo(target, def, confidence);
				
			}
				
		}

		return null;
	}
}
