package recuperaciondeinformacion.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CaseBaseFilter;
import jcolibri.cbrcore.Connector;
import jcolibri.datatypes.Text;
import jcolibri.exception.InitializingException;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.EncodingChangeException;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import recuperaciondeinformacion.utils.representation.NewsDescription;
import recuperaciondeinformacion.utils.representation.NewsSolution;

public class NewsConnector implements Connector {

	String newsFolder;
	int maxFilesPerCategory;
	
	public NewsConnector(String newsFolder, int maxFilesPerCategory)
	{
		this.newsFolder = newsFolder;
		this.maxFilesPerCategory = maxFilesPerCategory;
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCases(Collection<CBRCase> cases) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initFromXMLfile(URL file) throws InitializingException {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<CBRCase> retrieveAllCases() {
		ArrayList<CBRCase> cases = new ArrayList<CBRCase>();
		
		File mainFolder = new File(newsFolder);
		for(File folder : mainFolder.listFiles())
		{
			if(!folder.isDirectory())
				continue;
			int goodfiles = 0;
			for(File htmlFile: folder.listFiles())
			{
				if(htmlFile.isDirectory())
					continue;
				
				CBRCase _case = parseFile(htmlFile,folder.getName());
				if(_case != null)
				{
					if(goodfiles<maxFilesPerCategory)
					{
							cases.add(_case);
							goodfiles++;
					}
					
				}
			}
			System.out.println(folder.getName()+": "+goodfiles);
		}
		
		
		return cases;
	}

	private CBRCase parseFile(File htmlFile, String category) {
		
		//System.out.println(htmlFile);
		
		try {
			Parser parser = new Parser (htmlFile.getPath());
			NodeList list;
			try
			{
				list = parser.parse(new HasAttributeFilter("id","tabs-1"));
			}
			catch (EncodingChangeException ece)
			{
			    try
			    {
			        // reset the parser
			        parser.reset ();
			        // try again with the encoding now in force
			        list = parser.parse(new HasAttributeFilter("id","tabs-1"));
			    }			    
			    catch (ParserException pe){
			    	System.err.println("Parser error...");
			    	return null;
			    }

			}
			catch (ParserException pe){
				System.err.println("Parser error...");
				return null;
			}
			if(list.size()==0)
				return null;
			
			NodeList children = list.elementAt(0).getChildren();
			
			
			ImageTag imgTag = (ImageTag) children.extractAllNodesThatMatch(new TagNameFilter("img"),true).elementAt(0);
			if(imgTag == null)
				return null;
			String imgURL = imgTag.getImageURL();
			String img = imgURL.substring(imgURL.lastIndexOf('/')+1);
			
			String title = children.elementAt(1).toPlainTextString().trim();
			
			String text = "";
			
			boolean good = false;
			for(int i=2; i<children.size(); i++)
			{
				Node node = children.elementAt(i);
				if(node instanceof TagNode)
				{
					TagNode tag = (TagNode)node;
					String att = tag.getAttribute("class");
					if((att!=null) && att.equals("photo-placer photo-single"))
						good = true;
					if((att!=null) && att.equals("ads_standard"))
						good = false;
				}
				if(good)
					text += node.toPlainTextString().trim();
			}
			
			NewsDescription desc = new NewsDescription();
			desc.setId(htmlFile.getName());
			desc.setText(new Text(text));
			desc.setTitle(new Text(title));
			
			NewsSolution sol = new NewsSolution();
			sol.setId(htmlFile.getName());
			sol.setCategory(category);
			sol.setImgURL(img);
			
			CBRCase _case = new CBRCase();
			_case.setDescription(desc);
			_case.setSolution(sol);
			return _case;
			
		} catch (ParserException e) {
			System.err.println("Parser error...");
			return null;
		}

	}

	@Override
	public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeCases(Collection<CBRCase> cases) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		NewsConnector connector = new NewsConnector("data/noticias",150);
		connector.retrieveAllCases();
		//for(CBRCase _case: connector.retrieveAllCases())
			//System.out.println(_case);
	}

}
