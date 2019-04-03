package chat.rocket.android.chatrooms.ui

import DrawableHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import chat.rocket.android.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_fragment_sort_by.*

internal const val TAG = "SortByBottomSheetFragment"

class SortByBottomSheetFragment : BottomSheetDialogFragment() {
    private var isSortByName = false
    private var isUnreadOnTop = false
    private var isGroupByType = false
    private var isGroupByFavorites = false
    private val chatRoomFragment by lazy {
        activity?.supportFragmentManager?.findFragmentByTag(TAG_CHAT_ROOMS_FRAGMENT) as ChatRoomsFragment
    }
    private val filterDrawable by lazy { R.drawable.ic_filter_20dp }
    private val activityDrawable by lazy { R.drawable.ic_activity_20dp }
    private val unreadOnTopDrawable by lazy { R.drawable.ic_unread_20dp }
    private val groupByTypeDrawable by lazy { R.drawable.ic_group_by_type_20dp }
    private val groupByFavoritesDrawable by lazy { R.drawable.ic_favorites_20dp }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.bottom_sheet_fragment_sort_by, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        text_name.setOnClickListener {
            changeSortByTitle(getString(R.string.msg_sort_by_name))
            checkSelection(text_name, filterDrawable)
            uncheckSelection(text_activity, activityDrawable)
            isSortByName = true
            sortChatRoomsList()
        }

        text_activity.setOnClickListener {
            changeSortByTitle(getString(R.string.msg_sort_by_activity))
            checkSelection(text_activity, activityDrawable)
            uncheckSelection(text_name, filterDrawable)
            isSortByName = false
            sortChatRoomsList()
        }

        text_unread_on_top.setOnClickListener {
            isUnreadOnTop = if (isUnreadOnTop) {
                uncheckSelection(text_unread_on_top, unreadOnTopDrawable)
                false
            } else {
                checkSelection(text_unread_on_top, unreadOnTopDrawable)
                true
            }
            sortChatRoomsList()
        }

        text_group_by_type.setOnClickListener {
            isGroupByType = if (isGroupByType) {
                uncheckSelection(text_group_by_type, groupByTypeDrawable)
                false
            } else {
                checkSelection(text_group_by_type, groupByTypeDrawable)
                true
            }
            sortChatRoomsList()
        }

        text_group_by_favorites.setOnClickListener {
            isGroupByFavorites = if (isGroupByFavorites) {
                uncheckSelection(text_group_by_favorites, groupByFavoritesDrawable)
                false
            } else {
                checkSelection(text_group_by_favorites, groupByFavoritesDrawable)
                true
            }
            sortChatRoomsList()
        }
    }

    private fun changeSortByTitle(text: String) {
        text_sort_by.text = getString(R.string.msg_sort_by, text.toLowerCase())
    }

    private fun checkSelection(textView: TextView, @DrawableRes leftDrawable: Int) {
        context?.let {
            DrawableHelper.compoundLeftAndRightDrawable(
                textView,
                DrawableHelper.getDrawableFromId(leftDrawable, it),
                DrawableHelper.getDrawableFromId(R.drawable.ic_check, it)
            )
        }
    }

    private fun uncheckSelection(textView: TextView, @DrawableRes leftDrawable: Int) {
        context?.let {
            DrawableHelper.compoundLeftDrawable(
                textView,
                DrawableHelper.getDrawableFromId(leftDrawable, it)
            )
        }
    }

    private fun sortChatRoomsList() {
        chatRoomFragment.sortChatRoomsList(
            isSortByName,
            isUnreadOnTop,
            isGroupByType,
            isGroupByFavorites
        )
    }
}