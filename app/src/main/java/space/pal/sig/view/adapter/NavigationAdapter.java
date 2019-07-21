package space.pal.sig.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import space.pal.sig.R;
import space.pal.sig.model.NavigationItem;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavigationViewHolder> {

    public interface OnNavigationItemClickListener {
        void onNavigateFragment(NavigationItem navigationItem);
        void onNavigateActivity(NavigationItem navigationItem);
        void onNavigateExit();
    }

    private List<NavigationItem> navigationItemList = new ArrayList<>();
    private OnNavigationItemClickListener onNavigationItemClickListener;

    public NavigationAdapter(OnNavigationItemClickListener onNavigationItemClickListener) {
        this.onNavigationItemClickListener = onNavigationItemClickListener;
    }

    public void add(List<NavigationItem> navigationItems) {
        this.navigationItemList.clear();
        this.navigationItemList.addAll(navigationItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return navigationItemList.get(position).getType();
    }

    @NonNull
    @Override
    public NavigationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == NavigationItem.MENU_ITEM) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.navigation_item, parent, false);
            return new NavigationMenuHolder(view);
        } else {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.divider, parent, false);
            return new NavigationDividerHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationViewHolder holder, int position) {
        holder.render(navigationItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return navigationItemList.size();
    }

    abstract class NavigationViewHolder extends RecyclerView.ViewHolder {

        NavigationViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void render(NavigationItem navigationItem);
    }

    class NavigationDividerHolder extends NavigationViewHolder {

        NavigationDividerHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        void render(NavigationItem navigationItem) { }
    }

    class NavigationMenuHolder extends NavigationViewHolder {

        @BindView(R.id.icon) ImageView icon;
        @BindView(R.id.title) TextView title;
        private Context context;

        NavigationMenuHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = itemView.getContext();
        }

        @Override
        void render(NavigationItem navigationItem) {
            title.setText(navigationItem.getTitle());
            Glide.with(context)
                    .load(navigationItem.getIcon())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions.centerCropTransform())
                    .into(icon);
        }

        @OnClick(R.id.item)
        void onClick() {
            NavigationItem navigationItem = navigationItemList.get(getAdapterPosition());
            if (navigationItem.getFragment() == null && navigationItem.getIntent() == null) {
                onNavigationItemClickListener.onNavigateExit();
            } else if (navigationItem.getFragment() != null) {
                onNavigationItemClickListener.onNavigateFragment(navigationItem);
            } else if (navigationItem.getIntent() != null) {
                onNavigationItemClickListener.onNavigateActivity(navigationItem);
            }
        }
    }
}
