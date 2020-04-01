package io.santisme.ahoy.sources.main.topicdetail.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.local.TopicDetailHeaderModel
import io.santisme.ahoy.sources.additions.TimeOffsetRepository
import io.santisme.ahoy.sources.main.topicdetail.adapter.TopicDetailHolderProtocol
import kotlinx.android.synthetic.main.item_header_topic.view.*
import java.util.*

class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var header: TopicDetailHeaderModel? = null
        set(value) {
            field = value
            with(itemView) {
                tag = field
                field?.let {
                    topicTitleLabel.text = it.topicTitle
                    labelPosts.text = it.postsCount.toString()
                    labelViews.text = it.viewCount.toString()
                    topicContentLabel.text = it.topicContent
                    setTimeOffset(TimeOffsetRepository.getTimeOffset(it.updatedAt))
                }
            }
        }

    private fun setTimeOffset(timeOffset: TimeOffsetRepository.TimeOffset) {
        val quantityString = when (timeOffset.unit) {
            Calendar.YEAR -> R.plurals.years
            Calendar.MONTH -> R.plurals.months
            Calendar.DAY_OF_MONTH -> R.plurals.days
            Calendar.HOUR -> R.plurals.hours
            Calendar.MINUTE -> R.plurals.minutes
            else -> R.plurals.years
        }

        itemView.updatedAtLabel.text = if (timeOffset.amount != 0)
            itemView.context.resources.getQuantityString(
                quantityString,
                timeOffset.amount,
                timeOffset.amount
            )
        else
            itemView.context.resources.getString(R.string.minutes_zero)
    }
}