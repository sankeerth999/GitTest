package com.sankeerth.gitConnection;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

public class BranchesWithoutTravis {

	static String REMOTE_URL = "https://github.com/ruby/ruby.git";

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, GitAPIException {

		FileRepositoryBuilder builder = new FileRepositoryBuilder();

		Repository repository = builder
				.setGitDir(new File("C:/Users/reddy/AppData/Local/Temp/TestGitRepository8773457621826887633/.git"))
				.readEnvironment().findGitDir().build();
		Set<String> branchList = new TreeSet<>();

		try (Git git = new Git(repository)) {
			List<Ref> call = git.branchList().call();
			call = git.branchList().setListMode(ListMode.ALL).call();

			for (Ref ref : call) {
				branchList.add(ref.getName());
				RevWalk walk = new RevWalk(repository);

				RevCommit commit = walk.parseCommit(ref.getObjectId());
				RevTree tree = commit.getTree();

				TreeWalk treeWalk = new TreeWalk(repository);
				treeWalk.addTree(tree);
				treeWalk.setRecursive(true);
				Boolean fun = false;
				while (treeWalk.next()) {
					fun = treeWalk.getPathString().contentEquals(".travis.yml");
					if (fun == true) {
						branchList.remove(ref.getName());
					}
				}
			}
		}

		Iterator<String> itr = branchList.iterator();
		System.out.println("Branches without '.travis.yml':");
		while (itr.hasNext())
			System.out.println(itr.next());
		repository.close();
	}
}
