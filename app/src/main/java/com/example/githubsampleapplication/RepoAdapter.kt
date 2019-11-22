package com.example.githubsampleapplication

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsampleapplication.databinding.SingleItemModelLayoutBinding
import com.example.githubsampleapplication.model.RepositoryResponseModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoAdapter(val context: Context, val repoList: List<RepositoryResponseModel>) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private var checkedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding: SingleItemModelLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.single_item_model_layout, parent, false
        )
        return RepoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repoList[position]
        holder.itemModelLayoutBinding.repo = repo
        if (!repo.languageColor.isNullOrEmpty())
            holder.itemModelLayoutBinding.circleView.drawable.setColorFilter(
                Color.parseColor(repo.languageColor),
                PorterDuff.Mode.MULTIPLY
            )

        if (checkedPosition == position) {
            holder.itemModelLayoutBinding.repoDetailsTextview.visibility = View.VISIBLE
            holder.itemModelLayoutBinding.repoDetailsLayout.visibility = View.VISIBLE
        } else {
            holder.itemModelLayoutBinding.repoDetailsTextview.visibility = View.GONE
            holder.itemModelLayoutBinding.repoDetailsLayout.visibility = View.GONE
        }
        holder.itemModelLayoutBinding.root.setOnClickListener {

            if (checkedPosition != position) {
                notifyDataSetChanged();
                checkedPosition = position;
            } else {
                holder.itemModelLayoutBinding.repoDetailsTextview.visibility = View.GONE
                holder.itemModelLayoutBinding.repoDetailsLayout.visibility = View.GONE
                checkedPosition = -1
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return repoList[position].id.toLong()
    }

    inner class RepoViewHolder(val itemModelLayoutBinding: SingleItemModelLayoutBinding) :
        RecyclerView.ViewHolder(itemModelLayoutBinding.root)


}