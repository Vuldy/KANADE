package com.projects.kanade.predict

import android.R.attr.data
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.random.Random


/*fun PTTPtest(
    gender: Int,
    age: Int,
): Int {

    val dataFrame = DataFrame()
    dataFrame.addColumn(
        arrayOf(
            0, 1, 0, 1, 0, 1, 0, 1, 0, 1
        ).toList() as ArrayList<Int>,
        "Gender"
    )
    dataFrame.addColumn(
        arrayOf(16, 40, 21, 50, 60, 19, 44, 22, 31, 29).toList() as ArrayList<Int>,
        "Age"
    )
    /*dataFrame.addColumn(
        arrayOf(
            "Soft",
            "Soft",
            "Hard",
            "Hard",
            "Hard",
            "Soft",
            "Soft",
            "Soft",
            "Soft",
            "Hard"
        ).toList() as ArrayList<Float>,
        "Texture"
    )*/
    dataFrame.addColumn(
        arrayOf(200, 400, 250, 500, 400, 700, 150, 600, 300, 200).toList() as ArrayList<Int>,
        "Label"
    )

    // Set the data in the tree. Soon, the tree is created.
    val decisionTree = RandomForest(dataFrame)
    val sample = HashMap<String, Int>().apply {
        put("Gender", gender)
        put("Age", age)
        //put( "Texture" , "Soft" )
    }

    // Print the tree as a `HashMap`.

    //println( decisionTree.predict( sample ) )
    //println( decisionTree.toString() )

    return decisionTree.predict(sample)

}*/

data class TreeNode(
    val splitFeature: Int? = null,
    val splitValue: Double? = null,
    val left: TreeNode? = null,
    val right: TreeNode? = null,
    val value: Double? = null
)

fun mse(values: List<Double>): Double {
    if (values.isEmpty()) return 0.0
    val mean = values.sum() / values.size
    return values.map { (it - mean).pow(2) }.sum() / values.size
}

fun splitDataSet(data: List<Pair<List<Double>, Double>>, feature: Int, value: Double): Pair<List<Pair<List<Double>, Double>>, List<Pair<List<Double>, Double>>> {
    val left = data.filter { it.first[feature] <= value }
    val right = data.filter { it.first[feature] > value }
    return Pair(left, right)
}

fun getBestSplit(data: List<Pair<List<Double>, Double>>, features: List<Int>): Triple<Int, Double, Double> {
    var bestFeature = features[0]
    var bestValue = 0.0
    var bestScore = Double.MAX_VALUE

    for (feature in features) {
        val values = data.map { it.first[feature] }.distinct()
        for (value in values) {
            val (left, right) = splitDataSet(data, feature, value)
            val score = mse(left.map { it.second }) * left.size + mse(right.map { it.second }) * right.size
            if (score < bestScore) {
                bestFeature = feature
                bestValue = value
                bestScore = score
            }
        }
    }
    return Triple(bestFeature, bestValue, bestScore)
}

fun buildTree(data: List<Pair<List<Double>, Double>>, features: List<Int>, minSamples: Int = 5, maxDepth: Int = 5, depth: Int = 0): TreeNode {
    if (data.size <= minSamples || depth >= maxDepth) {
        val value = data.map { it.second }.average()
        return TreeNode(value = value)
    }

    val (bestFeature, bestValue, _) = getBestSplit(data, features)
    val (leftData, rightData) = splitDataSet(data, bestFeature, bestValue)

    if (leftData.isEmpty() || rightData.isEmpty()) {
        val value = data.map { it.second }.average()
        return TreeNode(value = value)
    }

    val leftNode = buildTree(leftData, features, minSamples, maxDepth, depth + 1)
    val rightNode = buildTree(rightData, features, minSamples, maxDepth, depth + 1)

    return TreeNode(splitFeature = bestFeature, splitValue = bestValue, left = leftNode, right = rightNode)
}

fun predict(node: TreeNode, features: List<Double>): Double {
    return when {
        node.value != null -> node.value
        features[node.splitFeature!!] <= node.splitValue!! -> predict(node.left!!, features)
        else -> predict(node.right!!, features)
    }
}

fun bootstrapSample(data: List<Pair<List<Double>, Double>>): List<Pair<List<Double>, Double>> {
    val n = data.size
    return List(n) { data[Random.nextInt(n)] }
}

fun getRandomFeatures(numFeatures: Int, maxFeatures: Int): List<Int> {
    return (0 until numFeatures).shuffled().take(maxFeatures)
}

class RandomForest(
    val numTrees: Int,
    val maxFeatures: Int,
    val minSamples: Int = 5,
    val maxDepth: Int = 5
) {
    private val trees = mutableListOf<TreeNode>()

    fun fit(data: List<Pair<List<Double>, Double>>) {
        val numFeatures = data[0].first.size
        for (i in 0 until numTrees) {
            val sample = bootstrapSample(data)
            val features = getRandomFeatures(numFeatures, maxFeatures)
            val tree = buildTree(sample, features, minSamples, maxDepth)
            trees.add(tree)
        }
    }

    fun predict(features: List<Double>): Double {
        val predictions = trees.map { predict(it, features) }
        return predictions.average()
    }
}

fun PTTPtest(
    month: Double,
    day: Double,
    working: Double,
    timeofday: Double,
    gender: Int,
    //age: Int,
): Int  {
    val data = listOf(
        listOf(1.0,4.0,1.0,1.0,0.0) to 452.0,
        listOf(1.0,4.0,1.0,1.0,1.0) to 500.0,
        listOf(1.0,4.0,1.0,1.0,1.0) to 1291.0,
        listOf(1.0,4.0,1.0,1.0,1.0) to 1264.0,
        listOf(1.0,4.0,1.0,1.0,1.0) to 1231.0,
        listOf(1.0,4.0,1.0,2.0,0.0) to 984.0,
        listOf(1.0,4.0,1.0,2.0,1.0) to 1324.0,
        listOf(1.0,4.0,1.0,2.0,1.0) to 837.0,
        listOf(1.0,4.0,1.0,2.0,1.0) to 1335.0,
        listOf(1.0,4.0,1.0,2.0,1.0) to 1223.0,
        listOf(1.0,4.0,1.0,2.0,1.0) to 1118.0,
        listOf(1.0,4.0,1.0,2.0,1.0) to 2035.0,
        listOf(1.0,4.0,1.0,2.0,1.0) to 1332.0,
        listOf(1.0,4.0,1.0,2.0,0.0) to 793.0,
        listOf(1.0,4.0,1.0,1.0,0.0) to 1108.0,
        listOf(1.0,4.0,1.0,1.0,0.0) to 656.0,
        listOf(1.0,7.0,0.0,1.0,0.0) to 578.0,
        listOf(1.0,7.0,0.0,1.0,0.0) to 347.0,
        listOf(1.0,7.0,0.0,1.0,1.0) to 804.0,
        listOf(1.0,7.0,0.0,1.0,1.0) to 859.0,
        listOf(1.0,7.0,0.0,1.0,1.0) to 997.0,
        listOf(1.0,7.0,0.0,1.0,1.0) to 609.0,
        listOf(1.0,7.0,0.0,1.0,0.0) to 1920.0,
        listOf(1.0,7.0,0.0,2.0,0.0) to 1013.0,
        listOf(1.0,7.0,0.0,2.0,1.0) to 655.0,
        listOf(1.0,7.0,0.0,2.0,0.0) to 613.0,
        listOf(1.0,7.0,0.0,2.0,1.0) to 762.0,
        listOf(1.0,7.0,0.0,2.0,0.0) to 781.0,
        listOf(1.0,7.0,0.0,2.0,1.0) to 1110.0,
        listOf(1.0,7.0,0.0,2.0,1.0) to 142.0,
        listOf(1.0,7.0,0.0,2.0,1.0) to 522.0,
        listOf(2.0,7.0,0.0,2.0,1.0) to 531.0,
        listOf(2.0,7.0,0.0,2.0,1.0) to 7.0,
        listOf(2.0,7.0,0.0,2.0,0.0) to 1007.0,
        listOf(2.0,7.0,0.0,2.0,0.0) to 591.0,
        listOf(2.0,7.0,0.0,2.0,0.0) to 468.0,
        listOf(2.0,7.0,0.0,2.0,0.0) to 625.0,
        listOf(2.0,7.0,0.0,2.0,1.0) to 539.0,
        listOf(2.0,7.0,0.0,2.0,0.0) to 779.0,
        listOf(2.0,7.0,0.0,2.0,0.0) to 527.0,
        listOf(2.0,4.0,1.0,1.0,0.0) to 601.0,
        listOf(2.0,4.0,1.0,1.0,1.0) to 559.0,
        listOf(2.0,4.0,1.0,1.0,0.0) to 420.0,
        listOf(2.0,4.0,1.0,1.0,1.0) to 842.0,
        listOf(2.0,4.0,1.0,1.0,1.0) to 1854.0,
        listOf(2.0,4.0,1.0,1.0,1.0) to 2289.0,
        listOf(2.0,4.0,1.0,1.0,0.0) to 454.0,
        listOf(2.0,4.0,1.0,1.0,1.0) to 1102.0,
        listOf(2.0,4.0,1.0,1.0,1.0) to 649.0,
        listOf(2.0,7.0,0.0,1.0,0.0) to 790.0,
        listOf(2.0,7.0,0.0,1.0,0.0) to 555.0,
        listOf(2.0,7.0,0.0,1.0,1.0) to 552.0,
        listOf(2.0,7.0,0.0,1.0,1.0) to 289.0,
        listOf(2.0,7.0,0.0,1.0,0.0) to 715.0,
        listOf(2.0,7.0,0.0,1.0,0.0) to 697.0,
        listOf(2.0,7.0,0.0,1.0,0.0) to 497.0,
        listOf(2.0,7.0,0.0,1.0,0.0) to 588.0,
        listOf(2.0,7.0,0.0,1.0,1.0) to 938.0,
        listOf(2.0,7.0,0.0,2.0,1.0) to 771.0,
        listOf(2.0,7.0,0.0,2.0,1.0) to 708.0,
        listOf(2.0,7.0,0.0,2.0,0.0) to 1042.0,
        listOf(2.0,7.0,0.0,2.0,1.0) to 1500.0,
        listOf(2.0,7.0,0.0,2.0,1.0) to 721.0,
        listOf(2.0,7.0,0.0,2.0,1.0) to 1434.0,
        listOf(2.0,3.0,1.0,1.0,1.0) to 1365.0,
        listOf(2.0,3.0,1.0,2.0,1.0) to 785.0,
        listOf(2.0,3.0,1.0,2.0,0.0) to 1958.0,
        listOf(2.0,3.0,1.0,2.0,0.0) to 2001.0,
        listOf(2.0,3.0,1.0,2.0,1.0) to 1201.0,
        listOf(2.0,3.0,1.0,2.0,1.0) to 819.0,
        listOf(2.0,3.0,1.0,2.0,1.0) to 734.0,
        listOf(3.0,7.0,0.0,1.0,0.0) to 555.0,
        listOf(3.0,7.0,0.0,1.0,1.0) to 1174.0,
        listOf(3.0,7.0,0.0,1.0,1.0) to 453.0,
        listOf(3.0,7.0,0.0,1.0,0.0) to 742.0,
        listOf(3.0,7.0,0.0,1.0,0.0) to 928.0,
        listOf(3.0,7.0,0.0,1.0,1.0) to 883.0,
        listOf(3.0,7.0,0.0,1.0,1.0) to 1278.0,
        listOf(3.0,7.0,0.0,1.0,0.0) to 515.0,
        listOf(3.0,7.0,0.0,2.0,0.0) to 691.0,
        listOf(3.0,7.0,0.0,2.0,1.0) to 754.0,
        listOf(3.0,7.0,0.0,2.0,1.0) to 698.0,
        listOf(3.0,7.0,0.0,2.0,1.0) to 1048.0,
        listOf(3.0,7.0,0.0,2.0,1.0) to 1147.0,
        listOf(3.0,7.0,0.0,2.0,0.0) to 836.0,
        listOf(3.0,4.0,1.0,1.0,1.0) to 461.0,
        listOf(3.0,4.0,1.0,1.0,0.0) to 622.0,
        listOf(3.0,4.0,1.0,1.0,1.0) to 2479.0,
        listOf(3.0,4.0,1.0,1.0,1.0) to 310.0,
        listOf(3.0,4.0,1.0,1.0,0.0) to 448.0,
        listOf(3.0,4.0,1.0,1.0,1.0) to 452.0,
        listOf(3.0,4.0,1.0,1.0,0.0) to 521.0,
        listOf(3.0,4.0,1.0,1.0,1.0) to 227.0,
        listOf(3.0,4.0,1.0,1.0,0.0) to 930.0,
        listOf(3.0,4.0,1.0,1.0,0.0) to 1061.0,
        listOf(3.0,4.0,1.0,2.0,1.0) to 739.0,
        listOf(3.0,4.0,1.0,2.0,1.0) to 1775.0,
        listOf(3.0,4.0,1.0,2.0,0.0) to 476.0,
        listOf(3.0,4.0,1.0,2.0,1.0) to 1100.0,
        listOf(3.0,4.0,1.0,2.0,1.0) to 884.0,
        listOf(3.0,4.0,1.0,2.0,1.0) to 555.0,
        listOf(3.0,4.0,1.0,2.0,0.0) to 945.0,
        listOf(3.0,4.0,1.0,2.0,1.0) to 473.0,
        listOf(3.0,4.0,1.0,2.0,1.0) to 734.0,
        listOf(3.0,4.0,1.0,2.0,1.0) to 1088.0,
        listOf(3.0,4.0,1.0,2.0,0.0) to 1119.0,
        listOf(3.0,4.0,1.0,2.0,0.0) to 1972.0,
        listOf(3.0,7.0,0.0,1.0,1.0) to 1160.0,
        listOf(3.0,7.0,0.0,1.0,0.0) to 491.0,
        listOf(3.0,7.0,0.0,1.0,0.0) to 772.0,
        listOf(3.0,7.0,0.0,1.0,0.0) to 484.0,
        listOf(3.0,7.0,0.0,1.0,1.0) to 479.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 561.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 851.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 677.0,
        listOf(4.0,4.0,1.0,2.0,0.0) to 653.0,
        listOf(4.0,4.0,1.0,2.0,0.0) to 693.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 735.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 751.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 653.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 1002.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 1050.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 957.0,
        listOf(4.0,7.0,0.0,1.0,1.0) to 519.0,
        listOf(4.0,7.0,0.0,1.0,1.0) to 1238.0,
        listOf(4.0,7.0,0.0,1.0,1.0) to 856.0,
        listOf(4.0,7.0,0.0,1.0,1.0) to 786.0,
        listOf(4.0,7.0,0.0,1.0,1.0) to 806.0,
        listOf(4.0,7.0,0.0,1.0,1.0) to 478.0,
        listOf(4.0,7.0,0.0,1.0,1.0) to 1156.0,
        listOf(4.0,7.0,0.0,1.0,1.0) to 1515.0,
        listOf(4.0,7.0,0.0,1.0,1.0) to 1130.0,
        listOf(4.0,7.0,0.0,1.0,0.0) to 1310.0,
        listOf(4.0,4.0,1.0,1.0,0.0) to 730.0,
        listOf(4.0,4.0,1.0,1.0,0.0) to 558.0,
        listOf(4.0,4.0,1.0,1.0,1.0) to 723.0,
        listOf(4.0,4.0,1.0,1.0,0.0) to 116.0,
        listOf(4.0,4.0,1.0,1.0,1.0) to 1079.0,
        listOf(4.0,4.0,1.0,1.0,1.0) to 788.0,
        listOf(4.0,4.0,1.0,1.0,0.0) to 616.0,
        listOf(4.0,4.0,1.0,1.0,0.0) to 589.0,
        listOf(4.0,4.0,1.0,1.0,0.0) to 570.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 971.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 730.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 864.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 700.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 933.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 1285.0,
        listOf(4.0,4.0,1.0,2.0,0.0) to 526.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 660.0,
        listOf(4.0,4.0,1.0,2.0,0.0) to 787.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 849.0,
        listOf(4.0,4.0,1.0,2.0,1.0) to 957.0,
        listOf(4.0,4.0,1.0,2.0,0.0) to 518.0,
        listOf(4.0,4.0,1.0,2.0,0.0) to 707.0,
        listOf(5.0,4.0,1.0,1.0,1.0) to 1249.0,
        listOf(5.0,4.0,1.0,1.0,1.0) to 1631.0,
        listOf(5.0,4.0,1.0,2.0,0.0) to 932.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 900.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 871.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 602.0,
        listOf(5.0,4.0,1.0,2.0,0.0) to 573.0,
        listOf(5.0,4.0,1.0,2.0,0.0) to 614.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 1239.0,
        listOf(5.0,4.0,1.0,2.0,0.0) to 961.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 470.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 585.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 1000.0,
        listOf(5.0,4.0,1.0,2.0,0.0) to 413.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 1151.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 286.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 872.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 888.0,
        listOf(5.0,7.0,0.0,1.0,0.0) to 655.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 375.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 741.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 243.0,
        listOf(5.0,7.0,0.0,1.0,0.0) to 687.0,
        listOf(5.0,7.0,0.0,1.0,0.0) to 429.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 630.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 371.0,
        listOf(5.0,7.0,0.0,1.0,0.0) to 459.0,
        listOf(5.0,7.0,0.0,1.0,0.0) to 880.0,
        listOf(5.0,7.0,0.0,1.0,0.0) to 200.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 782.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 1156.0,
        listOf(5.0,7.0,0.0,1.0,0.0) to 881.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 748.0,
        listOf(5.0,4.0,1.0,2.0,0.0) to 842.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 1102.0,
        listOf(5.0,4.0,1.0,2.0,0.0) to 1832.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 500.0,
        listOf(5.0,4.0,1.0,2.0,0.0) to 1059.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 1049.0,
        listOf(5.0,4.0,1.0,2.0,1.0) to 1259.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 885.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 372.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 504.0,
        listOf(5.0,7.0,0.0,1.0,0.0) to 503.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 866.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 1188.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 658.0,
        listOf(5.0,7.0,0.0,1.0,0.0) to 1023.0,
        listOf(5.0,7.0,0.0,1.0,1.0) to 904.0,
        listOf(6.0,7.0,0.0,2.0,0.0) to 223.0,
        listOf(6.0,7.0,0.0,2.0,1.0) to 397.0,
        listOf(6.0,7.0,0.0,2.0,1.0) to 620.0,
        listOf(6.0,7.0,0.0,2.0,1.0) to 630.0,
        listOf(6.0,7.0,0.0,2.0,1.0) to 614.0,
        listOf(6.0,7.0,0.0,2.0,1.0) to 425.0,
        listOf(6.0,7.0,0.0,2.0,1.0) to 1116.0,
        listOf(6.0,7.0,0.0,2.0,0.0) to 575.0,
        listOf(6.0,7.0,0.0,2.0,0.0) to 1036.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 1014.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 389.0,
        listOf(6.0,4.0,1.0,1.0,0.0) to 911.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 574.0,
        listOf(6.0,4.0,1.0,1.0,0.0) to 1588.0,
        listOf(6.0,4.0,1.0,1.0,0.0) to 644.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 715.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 763.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 10.0,
        listOf(6.0,4.0,1.0,1.0,0.0) to 1094.0,
        listOf(6.0,4.0,1.0,1.0,0.0) to 1813.0,
        listOf(6.0,4.0,1.0,1.0,0.0) to 1226.0,
        listOf(6.0,4.0,1.0,1.0,0.0) to 1118.0,
        listOf(6.0,4.0,1.0,1.0,0.0) to 879.0,
        listOf(6.0,4.0,1.0,2.0,1.0) to 663.0,
        listOf(6.0,4.0,1.0,2.0,0.0) to 310.0,
        listOf(6.0,4.0,1.0,2.0,1.0) to 457.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 601.0,
        listOf(6.0,4.0,1.0,1.0,0.0) to 919.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 503.0,
        listOf(6.0,4.0,1.0,1.0,0.0) to 1188.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 811.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 899.0,
        listOf(6.0,4.0,1.0,1.0,0.0) to 741.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 762.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 824.0,
        listOf(6.0,4.0,1.0,1.0,1.0) to 1780.0,
        listOf(6.0,4.0,1.0,2.0,1.0) to 1250.0,
        listOf(6.0,4.0,1.0,2.0,1.0) to 1524.0,
        listOf(6.0,4.0,1.0,2.0,1.0) to 633.0,
        listOf(6.0,4.0,1.0,2.0,0.0) to 588.0,
        listOf(6.0,4.0,1.0,2.0,0.0) to 104.0,
        listOf(6.0,4.0,1.0,2.0,1.0) to 2360.0,
        listOf(6.0,4.0,1.0,2.0,1.0) to 756.0,
        listOf(6.0,7.0,0.0,1.0,1.0) to 1031.0,
        listOf(6.0,7.0,0.0,1.0,1.0) to 13.0,
        listOf(6.0,7.0,0.0,1.0,0.0) to 1056.0,
        listOf(6.0,7.0,0.0,1.0,1.0) to 427.0,
        listOf(6.0,7.0,0.0,1.0,1.0) to 1048.0,
        listOf(6.0,7.0,0.0,1.0,0.0) to 520.0,
        listOf(6.0,7.0,0.0,1.0,1.0) to 638.0,
        listOf(6.0,7.0,0.0,1.0,1.0) to 853.0,
        listOf(6.0,7.0,0.0,1.0,1.0) to 724.0,
        listOf(6.0,7.0,0.0,1.0,1.0) to 808.0,
        listOf(6.0,7.0,0.0,1.0,0.0) to 833.0,
        listOf(6.0,7.0,0.0,1.0,0.0) to 1550.0,
        listOf(7.0,4.0,1.0,1.0,0.0) to 485.0,
        listOf(7.0,4.0,1.0,1.0,1.0) to 462.0,
        listOf(7.0,4.0,1.0,1.0,0.0) to 860.0,
        listOf(7.0,4.0,1.0,1.0,0.0) to 502.0,
        listOf(7.0,4.0,1.0,1.0,0.0) to 797.0,
        listOf(7.0,4.0,1.0,2.0,1.0) to 1062.0,
        listOf(7.0,4.0,1.0,2.0,0.0) to 1007.0,
        listOf(7.0,4.0,1.0,2.0,0.0) to 900.0,
        listOf(7.0,4.0,1.0,2.0,1.0) to 522.0,
        listOf(7.0,4.0,1.0,2.0,1.0) to 698.0,
        listOf(7.0,4.0,1.0,2.0,0.0) to 786.0,
        listOf(7.0,4.0,1.0,2.0,1.0) to 963.0,
        listOf(7.0,4.0,1.0,2.0,0.0) to 1514.0,
        listOf(7.0,4.0,1.0,2.0,1.0) to 694.0,
        listOf(7.0,4.0,1.0,2.0,1.0) to 1157.0,
        listOf(7.0,7.0,0.0,1.0,1.0) to 1802.0,
        listOf(7.0,7.0,0.0,1.0,1.0) to 966.0,
        listOf(7.0,7.0,0.0,1.0,1.0) to 1812.0,
        listOf(7.0,7.0,0.0,1.0,1.0) to 682.0,
        listOf(7.0,7.0,0.0,1.0,0.0) to 1067.0,
        listOf(7.0,7.0,0.0,1.0,1.0) to 550.0,
        listOf(7.0,7.0,0.0,1.0,0.0) to 716.0,
        listOf(7.0,7.0,0.0,1.0,0.0) to 810.0,
        listOf(7.0,7.0,0.0,1.0,1.0) to 959.0,
        listOf(7.0,7.0,0.0,1.0,1.0) to 882.0,
        listOf(7.0,7.0,0.0,1.0,0.0) to 744.0,
        listOf(7.0,7.0,0.0,2.0,1.0) to 690.0,
        listOf(7.0,7.0,0.0,2.0,1.0) to 639.0,
        listOf(7.0,7.0,0.0,2.0,0.0) to 656.0,
        listOf(7.0,7.0,0.0,2.0,1.0) to 497.0,
        listOf(7.0,7.0,0.0,2.0,1.0) to 523.0,
        listOf(7.0,7.0,0.0,2.0,1.0) to 436.0,
        listOf(7.0,7.0,0.0,2.0,1.0) to 549.0,
        listOf(7.0,7.0,0.0,2.0,1.0) to 477.0,
        listOf(7.0,7.0,0.0,2.0,1.0) to 942.0,
        listOf(7.0,7.0,0.0,2.0,1.0) to 2045.0,
        listOf(7.0,4.0,1.0,1.0,0.0) to 824.0,
        listOf(7.0,4.0,1.0,1.0,1.0) to 1327.0,
        listOf(7.0,4.0,1.0,1.0,0.0) to 756.0,
        listOf(7.0,4.0,1.0,1.0,1.0) to 1020.0,
        listOf(7.0,4.0,1.0,1.0,1.0) to 626.0,
        listOf(7.0,4.0,1.0,1.0,0.0) to 920.0,
        listOf(7.0,4.0,1.0,1.0,0.0) to 906.0,
        listOf(7.0,4.0,1.0,1.0,1.0) to 697.0,
        listOf(8.0,7.0,0.0,2.0,1.0) to 960.0,
        listOf(8.0,7.0,0.0,2.0,1.0) to 1584.0,
        listOf(8.0,7.0,0.0,2.0,1.0) to 540.0,
        listOf(8.0,7.0,0.0,2.0,0.0) to 420.0,
        listOf(8.0,7.0,0.0,2.0,1.0) to 600.0,
        listOf(8.0,7.0,0.0,2.0,0.0) to 600.0,
        listOf(8.0,7.0,0.0,2.0,0.0) to 900.0,
        listOf(8.0,7.0,0.0,2.0,1.0) to 1341.0,
        listOf(8.0,4.0,1.0,1.0,0.0) to 840.0,
        listOf(8.0,4.0,1.0,1.0,1.0) to 120.0,
        listOf(8.0,4.0,1.0,1.0,1.0) to 1260.0,
        listOf(8.0,4.0,1.0,1.0,1.0) to 360.0,
        listOf(8.0,4.0,1.0,1.0,1.0) to 960.0,
        listOf(8.0,4.0,1.0,1.0,1.0) to 480.0,
        listOf(8.0,4.0,1.0,1.0,0.0) to 540.0,
        listOf(8.0,4.0,1.0,1.0,0.0) to 1133.0,
        listOf(8.0,4.0,1.0,1.0,0.0) to 540.0,
        listOf(8.0,4.0,1.0,1.0,0.0) to 1200.0,
        listOf(8.0,4.0,1.0,1.0,0.0) to 780.0,
        listOf(8.0,4.0,1.0,1.0,0.0) to 360.0,
        listOf(8.0,4.0,1.0,1.0,1.0) to 540.0,
        listOf(8.0,4.0,1.0,1.0,1.0) to 660.0,
        listOf(8.0,4.0,1.0,1.0,1.0) to 480.0,
        listOf(8.0,4.0,1.0,1.0,1.0) to 540.0,
        listOf(8.0,4.0,1.0,1.0,1.0) to 720.0,
        listOf(8.0,7.0,0.0,2.0,0.0) to 516.0,
        listOf(8.0,7.0,0.0,2.0,1.0) to 14.0,
        listOf(8.0,7.0,0.0,2.0,0.0) to 540.0,
        listOf(8.0,7.0,0.0,2.0,0.0) to 780.0,
        listOf(8.0,7.0,0.0,2.0,0.0) to 480.0,
        listOf(8.0,7.0,0.0,2.0,1.0) to 540.0,
        listOf(8.0,7.0,0.0,2.0,1.0) to 240.0,
        listOf(8.0,7.0,0.0,2.0,0.0) to 1260.0,
        listOf(8.0,7.0,0.0,2.0,0.0) to 240.0,
        listOf(8.0,7.0,0.0,2.0,1.0) to 360.0,
        listOf(8.0,7.0,0.0,2.0,1.0) to 600.0,
        listOf(8.0,7.0,0.0,2.0,0.0) to 300.0,
        listOf(8.0,7.0,0.0,2.0,1.0) to 660.0,
        listOf(8.0,7.0,0.0,2.0,1.0) to 480.0,
        listOf(8.0,7.0,0.0,2.0,0.0) to 480.0,
        listOf(8.0,7.0,0.0,2.0,0.0) to 1020.0,
        listOf(8.0,4.0,1.0,1.0,0.0) to 2160.0,
        listOf(8.0,4.0,1.0,1.0,1.0) to 900.0,
        listOf(8.0,4.0,1.0,1.0,1.0) to 840.0,
        listOf(8.0,4.0,1.0,1.0,0.0) to 720.0,
        listOf(8.0,4.0,1.0,1.0,0.0) to 480.0,
        listOf(9.0,6.0,1.0,1.0,1.0) to 405.0,
        listOf(9.0,6.0,1.0,1.0,1.0) to 52.0,
        listOf(9.0,6.0,1.0,1.0,0.0) to 906.0,
        listOf(9.0,6.0,1.0,1.0,1.0) to 734.0,
        listOf(9.0,6.0,1.0,2.0,0.0) to 1081.0,
        listOf(9.0,6.0,1.0,2.0,0.0) to 1635.0,
        listOf(9.0,6.0,1.0,2.0,0.0) to 806.0,
        listOf(9.0,6.0,1.0,2.0,1.0) to 970.0,
        listOf(9.0,6.0,1.0,2.0,0.0) to 555.0,
        listOf(9.0,6.0,1.0,2.0,1.0) to 967.0,
        listOf(9.0,6.0,1.0,2.0,1.0) to 896.0,
        listOf(9.0,6.0,1.0,2.0,0.0) to 584.0,
        listOf(9.0,6.0,1.0,2.0,0.0) to 494.0,
        listOf(9.0,6.0,1.0,2.0,1.0) to 1041.0,
        listOf(9.0,4.0,1.0,1.0,1.0) to 696.0,
        listOf(9.0,4.0,1.0,1.0,0.0) to 861.0,
        listOf(9.0,4.0,1.0,1.0,1.0) to 1064.0,
        listOf(9.0,4.0,1.0,1.0,0.0) to 1112.0,
        listOf(9.0,4.0,1.0,1.0,0.0) to 646.0,
        listOf(9.0,4.0,1.0,1.0,1.0) to 1021.0,
        listOf(9.0,4.0,1.0,1.0,0.0) to 1096.0,
        listOf(9.0,4.0,1.0,1.0,1.0) to 1048.0,
        listOf(9.0,4.0,1.0,1.0,1.0) to 379.0,
        listOf(9.0,4.0,1.0,1.0,1.0) to 998.0,
        listOf(9.0,4.0,1.0,1.0,1.0) to 1251.0,
        listOf(9.0,4.0,1.0,1.0,1.0) to 777.0,
        listOf(9.0,4.0,1.0,1.0,0.0) to 764.0,
        listOf(9.0,7.0,0.0,1.0,0.0) to 455.0,
        listOf(9.0,7.0,0.0,1.0,1.0) to 323.0,
        listOf(9.0,7.0,0.0,1.0,0.0) to 226.0,
        listOf(9.0,7.0,0.0,1.0,0.0) to 250.0,
        listOf(9.0,7.0,0.0,1.0,0.0) to 227.0,
        listOf(9.0,7.0,0.0,1.0,1.0) to 410.0,
        listOf(9.0,7.0,0.0,1.0,0.0) to 431.0,
        listOf(9.0,7.0,0.0,1.0,0.0) to 876.0,
        listOf(9.0,7.0,0.0,2.0,0.0) to 772.0,
        listOf(9.0,7.0,0.0,2.0,0.0) to 735.0,
        listOf(9.0,7.0,0.0,2.0,1.0) to 925.0,
        listOf(9.0,7.0,0.0,2.0,1.0) to 741.0,
        listOf(9.0,7.0,0.0,2.0,0.0) to 472.0,
        listOf(9.0,7.0,0.0,2.0,1.0) to 578.0,
        listOf(9.0,7.0,0.0,2.0,1.0) to 1032.0,
        listOf(9.0,7.0,0.0,2.0,1.0) to 1998.0,
        listOf(9.0,7.0,0.0,2.0,0.0) to 1015.0,
        listOf(9.0,7.0,0.0,2.0,1.0) to 563.0,
        listOf(9.0,7.0,0.0,2.0,1.0) to 729.0,
        listOf(9.0,7.0,0.0,2.0,1.0) to 684.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 622.0,
        listOf(10.0,4.0,1.0,1.0,0.0) to 522.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 453.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 939.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 711.0,
        listOf(10.0,4.0,1.0,1.0,0.0) to 832.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 807.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 1475.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 941.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 780.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 1196.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 1041.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 1867.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 1931.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 1213.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 763.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 1185.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 1263.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 389.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 1394.0,
        listOf(10.0,7.0,0.0,1.0,1.0) to 863.0,
        listOf(10.0,7.0,0.0,1.0,0.0) to 797.0,
        listOf(10.0,7.0,0.0,1.0,1.0) to 664.0,
        listOf(10.0,7.0,0.0,1.0,0.0) to 807.0,
        listOf(10.0,7.0,0.0,1.0,0.0) to 392.0,
        listOf(10.0,7.0,0.0,1.0,1.0) to 1323.0,
        listOf(10.0,7.0,0.0,1.0,1.0) to 417.0,
        listOf(10.0,7.0,0.0,1.0,1.0) to 782.0,
        listOf(10.0,7.0,0.0,1.0,1.0) to 473.0,
        listOf(10.0,7.0,0.0,1.0,0.0) to 369.0,
        listOf(10.0,4.0,1.0,1.0,0.0) to 1013.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 1225.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 1084.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 823.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 1069.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 906.0,
        listOf(10.0,4.0,1.0,1.0,0.0) to 338.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 1069.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 885.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 698.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 700.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 1203.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 1124.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 934.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 571.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 682.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 1059.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 919.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 461.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 612.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 643.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 434.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 540.0,
        listOf(10.0,4.0,1.0,1.0,1.0) to 1431.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 950.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 802.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 1309.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 1370.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 2594.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 508.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 1101.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 387.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 566.0,
        listOf(10.0,4.0,1.0,2.0,1.0) to 1058.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 1493.0,
        listOf(10.0,4.0,1.0,2.0,0.0) to 490.0,
        listOf(11.0,7.0,0.0,1.0,1.0) to 966.0,
        listOf(11.0,7.0,0.0,1.0,1.0) to 631.0,
        listOf(11.0,7.0,0.0,1.0,1.0) to 575.0,
        listOf(11.0,7.0,0.0,1.0,0.0) to 468.0,
        listOf(11.0,7.0,0.0,1.0,1.0) to 472.0,
        listOf(11.0,7.0,0.0,1.0,0.0) to 1150.0,
        listOf(11.0,7.0,0.0,1.0,1.0) to 492.0,
        listOf(11.0,7.0,0.0,1.0,0.0) to 449.0,
        listOf(11.0,7.0,0.0,1.0,0.0) to 45.0,
        listOf(11.0,7.0,0.0,1.0,0.0) to 1189.0,
        listOf(11.0,7.0,0.0,2.0,1.0) to 130.0,
        listOf(11.0,7.0,0.0,2.0,1.0) to 714.0,
        listOf(11.0,7.0,0.0,2.0,0.0) to 986.0,
        listOf(11.0,7.0,0.0,2.0,0.0) to 692.0,
        listOf(11.0,7.0,0.0,2.0,1.0) to 693.0,
        listOf(11.0,7.0,0.0,2.0,1.0) to 685.0,
        listOf(11.0,7.0,0.0,2.0,1.0) to 266.0,
        listOf(11.0,7.0,0.0,2.0,1.0) to 984.0,
        listOf(11.0,4.0,1.0,1.0,1.0) to 423.0,
        listOf(11.0,4.0,1.0,1.0,0.0) to 1815.0,
        listOf(11.0,4.0,1.0,1.0,1.0) to 828.0,
        listOf(11.0,4.0,1.0,1.0,1.0) to 599.0,
        listOf(11.0,4.0,1.0,1.0,1.0) to 548.0,
        listOf(11.0,4.0,1.0,1.0,1.0) to 1086.0,
        listOf(11.0,4.0,1.0,1.0,1.0) to 591.0,
        listOf(11.0,4.0,1.0,1.0,0.0) to 465.0,
        listOf(11.0,4.0,1.0,1.0,1.0) to 721.0,
        listOf(11.0,4.0,1.0,1.0,1.0) to 580.0,
        listOf(11.0,4.0,1.0,1.0,1.0) to 1048.0,
        listOf(11.0,4.0,1.0,1.0,0.0) to 1460.0,
        listOf(11.0,4.0,1.0,1.0,0.0) to 691.0,
        listOf(11.0,4.0,1.0,1.0,0.0) to 428.0,
        listOf(12.0,7.0,0.0,1.0,1.0) to 403.0,
        listOf(12.0,7.0,0.0,1.0,1.0) to 385.0,
        listOf(12.0,7.0,0.0,1.0,1.0) to 315.0,
        listOf(12.0,7.0,0.0,1.0,1.0) to 508.0,
        listOf(12.0,7.0,0.0,1.0,1.0) to 1405.0,
        listOf(12.0,7.0,0.0,2.0,0.0) to 455.0,
        listOf(12.0,7.0,0.0,2.0,0.0) to 824.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 1011.0,
        listOf(12.0,7.0,0.0,2.0,0.0) to 787.0,
        listOf(12.0,7.0,0.0,2.0,0.0) to 138.0,
        listOf(12.0,7.0,0.0,2.0,0.0) to 1153.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 2473.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 271.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 377.0,
        listOf(12.0,7.0,0.0,2.0,0.0) to 316.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 325.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 242.0,
        listOf(12.0,7.0,0.0,2.0,0.0) to 803.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 532.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 510.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 1123.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 923.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 507.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 528.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 598.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 660.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 770.0,
        listOf(12.0,7.0,0.0,2.0,1.0) to 1086.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 712.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 859.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 1017.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 981.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 1196.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 537.0,
        listOf(12.0,4.0,1.0,1.0,0.0) to 527.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 925.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 468.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 625.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 513.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 880.0,
        listOf(12.0,4.0,1.0,1.0,0.0) to 671.0,
        listOf(12.0,4.0,1.0,1.0,0.0) to 437.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 994.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 603.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 942.0,
        listOf(12.0,4.0,1.0,1.0,0.0) to 381.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 376.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 532.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 802.0,
        listOf(12.0,4.0,1.0,1.0,1.0) to 766.0,
        listOf(12.0,4.0,1.0,2.0,0.0) to 481.0,
        listOf(12.0,4.0,1.0,2.0,1.0) to 597.0,
        listOf(12.0,4.0,1.0,2.0,0.0) to 713.0,
        listOf(12.0,4.0,1.0,2.0,0.0) to 613.0,
        listOf(12.0,4.0,1.0,2.0,0.0) to 633.0,
        listOf(12.0,4.0,1.0,2.0,0.0) to 843.0,
        listOf(12.0,4.0,1.0,2.0,1.0) to 407.0,
        listOf(12.0,4.0,1.0,2.0,1.0) to 938.0,
        listOf(12.0,4.0,1.0,2.0,1.0) to 1129.0,
        listOf(12.0,4.0,1.0,2.0,1.0) to 733.0,
        listOf(12.0,4.0,1.0,2.0,1.0) to 1030.0,
        listOf(12.0,4.0,1.0,2.0,0.0) to 711.0,

        )

    /*val data = mutableListOf <Pair<List<Double>, Double>>()

    //val predictedtime = mutableListOf<Double>()

    val filePath = "servicenewtxt.txt"// Replace with your CSV file path
    val file = File(filePath)

    file.forEachLine { line ->
        val tokens = line.split(",")
        // Process tokens here

        val variables = listOf(
        when (tokens[0]) {
            "January" -> 1.0
            "February" -> 2.0
            "March" -> 3.0
            "April" -> 4.0
            "May" -> 5.0
            "June" -> 6.0
            "July" -> 7.0
            "August" -> 8.0
            "September" -> 9.0
            "October" -> 10.0
            "November" -> 11.0
            "December" -> 12.0
            else -> {0.0}
        },

        when (tokens[1]) {
            "Monday" -> 2.0
            "Tuesday" -> 3.0
            "Wednesday" -> 4.0
            "Thursday" -> 5.0
            "Friday" -> 6.0
            "Saturday" -> 7.0
            "Sunday" -> 1.0
            else -> {0.0}
        },

        when (tokens[2]) {
            "TRUE" -> 1.0
            "FALSE" -> 0.0
            else -> {-1.0}
        },

        when (tokens[3]) {
            "morning" -> 1.0
            "afternoon" -> 2.0
            else -> {-1.0}
        },

        tokens[4].toDouble()
        )
        data.add(variables to tokens[5].toDouble())
    }*/

    val randomForest = RandomForest(numTrees = 10, maxFeatures = 1)
    randomForest.fit(data)
    val testSample = listOf(month, day, working, timeofday, gender.toDouble())
    val prediction = randomForest.predict(testSample)

    //println("Prediction for $testSample: ${prediction.roundToInt()}")

    return prediction.roundToInt()
}

/*@Preview
@Composable
fun PTTPprev() {
    PTTPtest()
}*/



