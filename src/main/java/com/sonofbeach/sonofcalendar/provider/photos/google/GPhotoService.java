package com.sonofbeach.sonofcalendar.provider.photos.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.OutOfLineContent;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;
import com.sonofbeach.sonofcalendar.model.photos.PhotoAlbum;
import com.sonofbeach.sonofcalendar.provider.authorization.GAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class GPhotoService {

    private final GAuthorizationService gAuthorizationService;

    @Autowired
    public GPhotoService(GAuthorizationService gAuthorizationService){
        this.gAuthorizationService = gAuthorizationService;
    }

    public PicasawebService getPicasawebService(String user) {
        Credential credential = gAuthorizationService.getFreshCredential(user);
        PicasawebService picasawebService = new PicasawebService(gAuthorizationService.getAPPLICATION_NAME());
        picasawebService.setOAuth2Credentials(credential);
        return picasawebService;
    }

    public List<String> getPhotoUrls(String user, String albumUrl) throws IOException, ServiceException {
        PicasawebService picasawebService = this.getPicasawebService(user);

        String googleuser = gAuthorizationService.getEmail(user);

        URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/"+googleuser+"/albumid/"+albumUrl);
        AlbumFeed feed = picasawebService.getFeed(feedUrl, AlbumFeed.class);

        List<String> photoUrls = new ArrayList<>();
        for(GphotoEntry gphotoEntry: feed.getEntries()){
            OutOfLineContent outOfLineContent = (OutOfLineContent) gphotoEntry.getContent();
            if(outOfLineContent.getUri() != null)
                photoUrls.add(changeToFullSizeUrl(outOfLineContent.getUri()));
        }
        return photoUrls;
    }

    private String changeToFullSizeUrl(String photoUrl){
        int lastIndex = photoUrl.lastIndexOf('/');
        return photoUrl.substring(0,lastIndex) + "/s0" + photoUrl.substring(lastIndex,photoUrl.length());
    }

    public List<PhotoAlbum> getAlbums(String user) {
        PicasawebService picasawebService = this.getPicasawebService(user);

        String googleuser = gAuthorizationService.getEmail(user);

        URL feedUrl = null;
        UserFeed myUserFeed = null;
        try {
            feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/"+googleuser+"?kind=album");
            myUserFeed = picasawebService.getFeed(feedUrl, UserFeed.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GAlbumFormatter.getPhotoAlbums(myUserFeed.getEntries());
    }


}
