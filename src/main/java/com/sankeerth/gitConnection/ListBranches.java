package com.sankeerth.gitConnection;

import java.util.Collection;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;

public class ListBranches {

	public static void main(String[] args) throws GitAPIException {
		
		Collection<Ref> refs = Git.lsRemoteRepository()
	            .setHeads(true)
	            .setRemote("https://github.com/ruby/ruby")
	            .call();
		for (Ref ref: refs){
			System.out.println("Branch: " + ref.getName());
		}
		}
	}

