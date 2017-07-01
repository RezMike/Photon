package io.github.rezmike.foton.ui.screens.photocard

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.github.rezmike.foton.R
import io.github.rezmike.foton.data.storage.PhotoCardRealm
import io.github.rezmike.foton.data.storage.TagRealm
import io.github.rezmike.foton.data.storage.UserRealm
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService
import io.realm.RealmList
import kotlinx.android.synthetic.main.ratio_image_16_9.view.*
import kotlinx.android.synthetic.main.screen_photocard.view.*
import javax.inject.Inject

class PhotocardView(context: Context, attrs: AttributeSet?) : AbstractView<PhotocardPresenter, PhotocardView>(context, attrs) {

    @Inject
    lateinit var picasso: Picasso

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<PhotocardScreen.Component>(context).inject(this)
    }

    fun showPhotoCardInfo(photoCard: PhotoCardRealm, user: UserRealm) {

        initUser(user)
        initPhotoCard(photoCard)
        initFlexBox(photoCard.tags)
    }

    private fun initUser(user: UserRealm) {
        author_name_tv.text = user.name
        album_count.text = user.albumCount.toString()
        photocard_count.text = user.photocardCount.toString()
        picasso.load(user.avatar)
                .resize(80, 80)
                .placeholder(R.drawable.ic_custom_profile_black_24dp)
                .into(author_avatar_img)
    }

    private fun initPhotoCard(photoCard: PhotoCardRealm) {
        food_info_tv.text = photoCard.title
        picasso.load(photoCard.photo)
                .resize(300, 200)
                .into(food_img)
    }

    private fun initFlexBox(tags: RealmList<TagRealm>) {
        flex_box.removeAllViews()
        tags.forEach {
            flex_box.addView(createTagTv(it))
        }
    }

    private fun createTagTv(item: TagRealm): TextView {
        val textView = TextView(context)
        textView.setBackgroundResource(R.drawable.borders_tags)
        textView.text = "#${item.tag.toLowerCase().replace(" ", "")}"
        textView.gravity = Gravity.START
        textView.setPadding(16, 16, 16, 16)
        textView.setTextColor(resources.getColor(R.color.black))
        textView.textSize = 16f
        return textView
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
