package com.example.socialapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.text.SimpleDateFormat
import java.util.Locale

class PostAdapter(options: FirestoreRecyclerOptions<Post>,private val onDataChanges: OnDataChanges) : FirestoreRecyclerAdapter<Post, ViewHolder>(
    options
) {
    interface OnDataChanges{
        fun datachange()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.itempost,parent,false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Post) {
        Glide.with(holder.userImage.context).load(model.userImage).circleCrop().into(holder.userImage)
        holder.userName.text=model.username
        holder.post.text=model.post
        holder.createdAt.text=getTimeAgo(model.currentTime)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        onDataChanges.datachange()
    }

    private fun getTimeAgo(timestamp: String?): String {
        if (timestamp == null) {
            return "N/A"
        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = dateFormat.parse(timestamp) ?: return "Invalid Timestamp"

        val currentTime = System.currentTimeMillis()
        val timeDiff = currentTime - date.time

        val seconds = timeDiff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            seconds < 60 -> "just now"
            minutes < 60 -> "$minutes minutes ago"
            hours < 24 -> "$hours hours ago"
            days == 1L -> "yesterday"
            else -> SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
        }
    }
}
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userImage:ImageView=itemView.findViewById(R.id.userImage)
    var userName:TextView=itemView.findViewById(R.id.userName)
    var createdAt:TextView=itemView.findViewById(R.id.createdAt)
    val post:TextView=itemView.findViewById(R.id.postTitle)
    val likedButton:ImageButton=itemView.findViewById(R.id.likeButton)
}