package io.santisme.ahoy.sources.main.topiclist.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.santisme.ahoy.BuildConfig
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.local.TopicCellModel
import io.santisme.ahoy.sources.additions.TimeOffsetRepository
import kotlinx.android.synthetic.main.item_topic.view.*
import java.util.*

class TopicHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var topic: TopicCellModel? = null
        set(value) {
            field = value
            with(itemView) {
                tag = field
                field?.let {
                    it.avatarTemplate?.let { avatarTemplate ->
                        requestUserAvatar(avatarTemplate = avatarTemplate)

                    } ?: run {
                        userImage.setImageDrawable(it.userImage?.drawable)
                    }

                    labelTitle.text = it.topicTitle
                    labelPosts.text = it.postsCount.toString()
                    labelViews.text = it.viewCount.toString()
                    setTimeOffset(TimeOffsetRepository.getTimeOffset(it.createdAt))
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

        itemView.createdAtLabel.text = if (timeOffset.amount != 0)
            itemView.context.resources.getQuantityString(
                quantityString,
                timeOffset.amount,
                timeOffset.amount
            )
        else
            itemView.context.resources.getString(R.string.minutes_zero)
    }

    private fun requestUserAvatar(avatarTemplate: String) {
        Glide.with(itemView)
            .load(
                "https://${BuildConfig.DiscourseDomain}${avatarTemplate.replace(
                    "{size}",
                    "64"
                )}"
            )
            .placeholder(R.drawable.user_avatar)
            .centerCrop()
            .into(itemView.userImage)
    }
}