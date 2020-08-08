package forest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mvc.Model;
import mvc.View;

/**
 * 樹状整列におけるMVCのモデル（M）を担うクラス。
 */
public class ForestModel extends Model
{
	/**
	 * 樹状整列それ自身を記憶しておくフィールド。
	 */
	private Forest forest;

	/**
	 * このクラスのインスタンスを生成するコンストラクタ。
	 * @param aFile 樹状整列データファイル
	 */
	public ForestModel(File aFile)
	{
		super();

		// フォレストのインスタンスを生成して保持し、樹状整列データファイルを読み込み、樹状整列させる。
		this.forest = new Forest();

		try
		{
			BufferedReader readStream = new BufferedReader(new InputStreamReader(new FileInputStream(aFile)));
			this.read(readStream);
			readStream.close();

		}
		catch (FileNotFoundException anException)
		{
			System.err.println(anException);
			throw new RuntimeException(anException);
		}
		this.arrange();

		return;
	}

	/**
	 * アニメーションを行うメソッド。
	 */
	public void animate()
	{
		// フォレストの樹状整列に自分を引数で渡すので、propagateによりアニメーションが行われる。
		this.forest.arrange(this);
		this.changed();

		return;
	}

	/**
	 * 樹状整列を行うメソッド。
	 */
	public void arrange()
	{
		// フォレストの樹状整列に引数無しですので、アニメーションは行われない。
		this.forest.arrange();
		this.changed();

		return;
	}

	/**
	 * 自分自身が変化したことを依存物たちに連絡（updateを依頼）するメソッド。
	 */
	@Override
	public void changed()
	{
		// 樹状整列の境界領域を求め、その領域と高さの画像を生成する。
		Rectangle aRectangle = this.forest.bounds();
		this.picture(new BufferedImage(aRectangle.width, aRectangle.height, BufferedImage.TYPE_INT_RGB));

		// 画像の描画コンテクスト（グラフィックス）を取り出し、それを背景で塗りつぶす。
		Graphics aGraphics = this.picture().createGraphics();
		aGraphics.setColor(Color.WHITE);
		aGraphics.fillRect(0, 0, aRectangle.width, aRectangle.height);

		// 樹状整列を画像の描画コンテクスト（グラフィックス）に描き出す。
		this.forest.draw(aGraphics);

		// モデルが変化していることを依存物であるビューたちへ連絡（updateを依頼）する。
		this.dependents.forEach((View aView) -> { aView.update(); });

		return;
	}

	/**
	 * 樹状整列それ自身を応答するメソッド。
	 * @return 樹状整列それ自身
	 */
	public Forest forest()
	{
		return this.forest;
	}

	/**
	 * 樹状整列データファイルから樹状整列それ自身を生成するメソッド。
	 * @param aFile 樹状整列データファイル
	 */
	protected void read(BufferedReader readStream)
	{
		// 樹状整列データファイルを読み込んで、ツリー（木）たち、ノード（節）たち、ブランチ（枝）たち、を割り出す。
		List<String> trees = new ArrayList<String>();
		List<String> nodes = new ArrayList<String>();
		List<String> branches = new ArrayList<String>();
		String string = new String();
		// treesかnodesかbranchesのどの状態か区別する
		String status = new String();
		// 一行ずつ読み込む
		while((string = readStream.readLine()) != null)
		{
			switch(string) {
				case Constants.TagOfTrees:
					status = Constants.TagOfTrees;
					string = readStream.readLine();
					break;
				case Constants.TagOfNodes:
					status = Constants.TagOfNodes;
					string = readStream.readLine();
					break;
				case Constants.TagOfBranches:
					status = Constants.TagOfBranches;
					string = readStream.readLine();
					break;
			}
			if(status.equals(Constants.TagOfTrees)) {
				trees.add(string);
			} else if(status.equals(Constants.TagOfNodes)) {
				nodes.add(string);
			} else if(status.equals(Constants.TagOfBranches)) {
				branches.add(string);
			}
		}
		/**********
		new Condition(() ->
		{
			string.set(this.readLine(readStream));
			return (string.get()) != null;
		}).whileTrue(() ->
		{
			new Condition(() ->
				string.get().equals(Constants.TagOfTrees)
			).ifTrue(() ->
			{
				string.set(this.readLine(readStream));
				new Condition(() ->
					string.get() != null && !(string.get().equals(Constants.TagOfNodes))
				).whileTrue(() ->
				{
					trees.add(string.get());
					string.set(this.readLine(readStream));
				});
			});

			new Condition(() ->
				string.get() != null && string.get().equals(Constants.TagOfNodes)
			).whileTrue(() ->
			{
				string.set(this.readLine(readStream));
				new Condition(() ->
					string.get() != null && !(string.get().equals(Constants.TagOfNodes))
				).whileTrue(() ->
				{
					nodes.add(string.get());
					string.set(this.readLine(readStream));
				});
			});

			new Condition(() ->
				string.get() != null && string.get().equals(Constants.TagOfBranches)
			).whileTrue(() ->
			{
				string.set(this.readLine(readStream));
				new Condition(() ->
					string.get() != null
				).whileTrue(() ->
				{
					branches.add(string.get());
					string.set(this.readLine(readStream));
				});
			});
		});

		 **********/

		// ノードたちを生成して登録する。
		Node[] nodeArray = new Node[nodes.size()-1];

		for(String node : nodes)
		{
			String[] stringArray = node.split(", ");
			if(stringArray.length == 2)
			{
				Integer anIndex = Integer.parseInt(stringArray[0])-1; // Node番号を格納
				Node aNode = new Node(stringArray[1]); // Nodeの名前を格納
				nodeArray[anIndex] = aNode;
				this.forest.addNode(aNode);
			}
		}

		/**********
		nodes.forEach((String aString) ->
		{
			String[] stringArray = aString.split(", ");
			new Condition(() -> stringArray.length == 2).ifTrue(() ->
			{
				Integer anIndex = Integer.parseInt(stringArray[0]) - 1;
				Node aNode = new Node(stringArray[1]);
				nodeArray[anIndex] = aNode;
				this.forest.addNode(aNode);
			});
		});
		**********/

		// ブランチたちを生成して登録する。
		for(String branch : branches)
		{
			String[] stringArray = branch.split(", ");
			if(stringArray.length == 2)
			{
				Node fromNode = nodeArray[Integer.parseInt(stringArray[0])-1];
				Node toNode = nodeArray[Integer.parseInt(stringArray[1])-1];
				Branch aBranch = new Branch(fromNode, toNode); // Nodeの名前を格納
				this.forest.addBranch(aBranch);
			}
		}
		/**********
		branches.forEach((String aString) ->
		{
			String[] stringArray = aString.split(", ");
			new Condition(() -> stringArray.length == 2).ifTrue(() -> 
			{
				Node fromNode = nodeArray[Integer.parseInt(stringArray[0]) - 1];
				Node toNode = nodeArray[Integer.parseInt(stringArray[1]) - 1];

				Branch aBranch = new Branch(fromNode, toNode);
				this.forest.addBranch(aBranch);
			});
		});
		**********/
		return;
	}
	
	/**
	 * 樹状整列の根元（ルート）になるノードを探し出して応答するメソッド。
	 * @return ルートノード、ただし、見つからないときはnullを応答する。
	 */
	public Node root()
	{
		List<Node> roots = this.roots();

		return (roots.size() > 0) ? (roots.get(0)) : (null);
	}
	/**
	 * 
	 * @return
	 */
	public List<Node> roots()
	{
		return this.forest().rootNodes();
	}
}