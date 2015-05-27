/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.silvergobletgames.leadcrystal.core;

import com.codedisaster.steamworks.SteamAPI;
import com.codedisaster.steamworks.SteamLeaderboardEntriesHandle;
import com.codedisaster.steamworks.SteamLeaderboardEntry;
import com.codedisaster.steamworks.SteamLeaderboardHandle;
import com.codedisaster.steamworks.SteamPublishedFileID;
import com.codedisaster.steamworks.SteamRemoteStorage;
import com.codedisaster.steamworks.SteamRemoteStorageCallback;
import com.codedisaster.steamworks.SteamResult;
import com.codedisaster.steamworks.SteamUGCHandle;
import com.codedisaster.steamworks.SteamUser;
import com.codedisaster.steamworks.SteamUserStats;
import com.codedisaster.steamworks.SteamUserStatsCallback;
import com.codedisaster.steamworks.SteamUtils;
import java.nio.ByteBuffer;

/**
 *
 * @author Mike
 */
public class Steam {
    
    //instance
    private static Steam instance;
    
    private SteamUser user;
    private SteamUserStats userStats;
    private SteamRemoteStorage remoteStorage;
    private SteamUtils utils;
    
    private SteamUserStatsCallback userStatsCallback = new SteamUserStatsCallback() {
		@Override
		public void onUserStatsReceived(long gameId, long userId, SteamResult result)
                {
			System.out.println("User stats received: gameId=" + gameId + ", userId=" + userId +
					", result=" + result.toString());

			int numAchievements = userStats.getNumAchievements();
			System.out.println("Num of achievements: " + numAchievements);

			for (int i = 0; i < numAchievements; i++)
                        {
				String name = userStats.getAchievementName(i);
				boolean achieved = userStats.isAchieved(name, false);
				System.out.println("# " + i + " : name=" + name + ", achieved=" + (achieved ? "yes" : "no"));
			}
		}

		@Override
		public void onUserStatsStored(long gameId, SteamResult result) 
                {
			System.out.println("User stats stored: gameId=" + gameId +
					", result=" + result.toString());
		}

		@Override
		public void onLeaderboardFindResult(SteamLeaderboardHandle leaderboard, boolean found)
                {
	
		}

		@Override
		public void onLeaderboardScoresDownloaded(SteamLeaderboardHandle leaderboard,SteamLeaderboardEntriesHandle entries, int numEntries) 
                {

			System.out.println("Leaderboard scores downloaded: handle=" + leaderboard.toString() +
					", entries=" + entries.toString() + ", count=" + numEntries);


		}

		@Override
		public void onLeaderboardScoreUploaded(boolean success,SteamLeaderboardHandle leaderboard, int score, boolean scoreChanged, int globalRankNew, int globalRankPrevious) 
                {

			System.out.println("Leaderboard score uploaded: " + (success ? "yes" : "no") +
					", handle=" + leaderboard.toString() +
					", score=" + score +
					", changed=" + (scoreChanged ? "yes" : "no") +
					", globalRankNew=" + globalRankNew +
					", globalRankPrevious=" + globalRankPrevious);
		}
	};
    private SteamRemoteStorageCallback remoteStorageCallback = new SteamRemoteStorageCallback() {
		@Override
		public void onFileShareResult(SteamUGCHandle fileHandle, String fileName, SteamResult result) 
                {
			System.out.println("Remote storage file share result: handle='" + fileHandle.toString() +
					", name=" + fileName + "', result=" + result.toString());
		}

		@Override
		public void onDownloadUGCResult(SteamUGCHandle fileHandle, SteamResult result) 
                {
//			System.out.println("Remote storage download UGC result: handle='" + fileHandle.toString() +
//					"', result=" + result.toString());
//
//			ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
//			int offset = 0, bytesRead;
//
//			do {
//				bytesRead = remoteStorage.ugcRead(fileHandle, buffer, buffer.limit(), offset,
//					SteamRemoteStorage.UGCReadAction.ContinueReadingUntilFinished);
//				offset += bytesRead;
//			} while (bytesRead > 0);
//
//			System.out.println("Read " + offset + " bytes from handle=" + fileHandle.toString());
		}

		@Override
		public void onPublishFileResult(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result)
                {
			System.out.println("Remote storage publish file result: publishedFileID=" + publishedFileID.toString() +
					", needsToAcceptWLA=" + needsToAcceptWLA + ", result=" + result.toString());
		}

		@Override
		public void onUpdatePublishedFileResult(SteamPublishedFileID publishedFileID, boolean needsToAcceptWLA, SteamResult result)
                {
			System.out.println("Remote storage update published file result: publishedFileID=" + publishedFileID.toString() +
					", needsToAcceptWLA=" + needsToAcceptWLA + ", result=" + result.toString());
		}
	};
    
    private Steam()
    {
        
        user = new SteamUser(SteamAPI.getSteamUserPointer());

        userStats = new SteamUserStats(SteamAPI.getSteamUserStatsPointer(), userStatsCallback);

        remoteStorage = new SteamRemoteStorage(SteamAPI.getSteamRemoteStoragePointer(), remoteStorageCallback);
        
        utils = new SteamUtils(SteamAPI.getSteamUtilsPointer());
        
        //initialize stats
        userStats.requestCurrentStats();
        
        
        //start callback thread
        Thread callbackThread = new Thread(){
         @Override
         public void run(){
                while ( SteamAPI.isSteamRunning()) 
                {
                    

                       // process callbacks
                       SteamAPI.runCallbacks();

                       try {
                               // sleep a little (Steam says it should poll at least 15 times/second)
                               Thread.sleep(1000 / 15);
                       } catch (InterruptedException e) {
                               // ignore
                       }
               }
          }};       
        callbackThread.start();
        
      
          
    }
    
    public static Steam getInstance()
    {
        if( instance == null)
            instance = new Steam();
        
        return instance;
    }
    
    
    public int getNumAchievements()
    {
        return userStats.getNumAchievements();        
    }
    
    public void unlockAchievement(String achievement)
    {  
         userStats.setAchievement(achievement);
         userStats.storeStats();
    }
    
    public void clearAchievement(String achievement)
    {
        userStats.clearAchievement(achievement);
        userStats.storeStats();
    }
    
    
    
    
    
    
    
}
