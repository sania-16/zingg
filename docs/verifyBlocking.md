---
description: >-
  Understanding how blocking is working before running match or link
---

# Verification of Blocking Data

Sometimes Zingg jobs are slow or fail due to a poorly learnt blocking tree. This can happen due to a variety of reasons. It can happen when:
- A user adds significantly larger training samples compared to the labelling learnt by Zingg. The manually added training samples may have the same type of columns and blocking rules learnt are not generic enough. For example, providing California state only training data when the matching is using the State column and data has multiple states.
- When there is a natural bias in the data with lots of null columns used in matching.
- When sufficient labeling has not been done.
- Having lots of non differentiating columns.

If we have an understanding of how blocking is working before deciding to run a match or link job, we get a better idea whether we need to add more training data for getting better results.

### The verifyBlocking phase is run as follows:

`./scripts/zingg.sh --phase verifyBlocking --conf <path to conf> <optional --zinggDir <location of model>>`

The output contains two directories - zinggDir/modelId/blocks/timestamp/counts and zinggDir/modelId/blocks/timestamp/blockSamples. We can see the counts per block and the top 10% records associated with the top 3 blocks by counts in the directories respectively.

For Enterprise Snowflake, we will be having tables with the names - zingg/modelId/blocks/timestamp/counts where we can see the counts per block and zingg/modelId/blocks/timestamp/blockSamples/hash where we can see the top 10% records associated with the top 3 blocks by counts in these tables respectively.