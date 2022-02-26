package pt.ipp.isep.decidergame.presentation.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import pt.ipp.isep.decidergame.R

class GameOverDialogFragment(
    private val gameTime: Long,
    private val numMoves: Int,
    private val scorePeak: Int
) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            val view = View.inflate(context, R.layout.dialog_gameover, null)
            builder.setView(bindInfo(view))
                .setNegativeButton(R.string.close) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private fun bindInfo(view: View) : View {
        view.findViewById<TextView>(R.id.tv_game_time).text = String.format("%.1f", gameTime / 1000.0)
        view.findViewById<TextView>(R.id.tv_num_moves).text = "$numMoves"
        view.findViewById<TextView>(R.id.tv_score_peak).text = "$scorePeak"
        return view
    }

}